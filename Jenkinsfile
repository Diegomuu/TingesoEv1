pipeline{
    agent any
    tools{
        maven "maven"

    }
    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Diegomuu/TingesoEv1']])
                dir("KartingBack"){
                    bat "mvn clean install"
                }
            }
        }
        stage("Test"){
            steps{
                dir("KartingBack"){
                    bat "mvn test"
                }
            }
        }        
        stage("Build and Push Docker Image"){
            steps{
                dir("KartingBack"){
                    script{
                         withDockerRegistry(credentialsId: 'docker-credentials'){
                            bat "docker build -t diegomuu/KartingBack ."
                            bat "docker push diegomuu/KartingBack"
                        }
                    }                    
                }
            }
        }
    }
}