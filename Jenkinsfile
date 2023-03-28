def String pomVersion = ''
pipeline {
    environment {
        NAMESPACE = "${env.BRANCH_NAME == "main" ? "tfm-prod-agat-prog" : "tfm-pre-agat-prog"}"
        DEPLOY = "${env.BRANCH_NAME == "main" || env.BRANCH_NAME == "develop" ? "true" : "false"}"
        BUILD = "${env.BRANCH_NAME == "develop" || env.BRANCH_NAME.startsWith("release") || env.BRANCH_NAME == "main" ? "true" : "false"}"
        REGISTRY = 'agatalba/tfm-mca-filemanagement-front'
    }
	options {
	    buildDiscarder(logRotator(numToKeepStr: "2"))
		disableConcurrentBuilds()  
	}    
    agent any
    tools {
        maven 'maven-3_8_6' 
    }
    
    stages {
        stage('Unit Test') {
            steps {
                script {
                    pomVersion = sh script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true
                }
                echo "env.BRANCH_NAME -- ${env.BRANCH_NAME}"
                echo "BUILD -- ${BUILD}"
                echo "DEPLOY -- ${DEPLOY}" 
                echo "version -- ${pomVersion}"    
                sh "mvn clean test"                
            }
        }
        stage('Deploy dependencies') {
            when {
                environment name: 'BUILD', value: 'true'
            }        
            steps {    
			    configFileProvider(
			        [configFile(fileId: 'files-maven-config-file', variable: 'MAVEN_SETTINGS')]) {
			        sh 'mvn -s $MAVEN_SETTINGS deploy -DskipTests'
			    }            
            }
        } 
        stage('Build image') {
            when {
                environment name: 'BUILD', value: 'true'
            }
            steps {
            	withCredentials([usernamePassword(credentialsId: 'dockerhub-user', passwordVariable: 'pass', usernameVariable: 'user')]) {
            		echo "version -- ${REGISTRY}" 
                	sh "mvn compile com.google.cloud.tools:jib-maven-plugin:3.2.0:build -Dimage=${REGISTRY}:${pomVersion} -DskipTests -Djib.to.auth.username=${user} -Djib.to.auth.password=${pass}"                
            	}
            }
        }                
        stage('Deploy into Kubernetes') {
            when {
                environment name: 'DEPLOY', value: 'true'
            }          
            agent {
                docker {
                    image 'dtzar/helm-kubectl'
                    args  '-u root -v /home/agat/.kube:/root/.kube'
                }
            }  
            steps {
                sh "helm upgrade -n ${NAMESPACE} -f helm/values.yaml --set namespace=${NAMESPACE} --set image.tag='${pomVersion}' front-release helm/"
            }
        }              
    }
}
