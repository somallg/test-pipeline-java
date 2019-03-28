def getComitter() {
    return sh(script: 'git show -s --pretty=%an', returnStdout: true)
}

def getComitterEmail() {
    return sh(script: 'git show -s --pretty=%ae', returnStdout: true)
}

def getBranch() {
    return sh(script: 'git show -s --pretty=%ae', returnStdout: true)
}

def traineeCode = 'trainee-code'

def testCode = 'test-code'

def TRAINEE_LIST = ['duongtq', 'tuanna', 'somallg']
def EXCERCISE_LIST = ['exo1', 'exo2']

pipeline {
    agent none
    environment {
        traineeAccount = ''
        traineeEmail = ''
        exercise = ''
    }

    options {
        skipDefaultCheckout(true)
        checkoutToSubdirectory('trainee')
    }

    stages {
        stage('Checkout') {
            agent any
            steps {
                dir(traineeCode) {
                    echo 'Checkout Trainee Code'
                    checkout scm
                }
                dir(testCode) {
                    echo 'Checkout Test Code'
                    git(branch: 'master',
                            url: 'https://github.com/somallg/test-pipeline-java-unit-test')
                }
            }
        }
        stage('Get Trainee Information') {
            agent any
            steps {
                dir(traineeCode) {
                    script {
                        traineeAccount = getComitter()
                        traineeEmail = getComitterEmail()
                        exercise = env.BRANCH_NAME
                    }
                }
            }
        }
        stage('Validate Trainee Account and Exercise') {
            agent any
            when {
                anyOf {
                    expression {
                        !TRAINEE_LIST.contains(traineeAccount)
                    }
                }
            }
            steps {
                echo 'Trainee is not in Trainee List. Make sure you have config Git username properly'
                sh 'exit 1'
            }
        }
        stage('Merge Code') {
            agent {
                docker 'instrumentisto/rsync-ssh'
            }
            steps {
                echo 'Merge Trainee Code and Test Code'
                sh "rsync -a ${testCode}/ ${traineeCode}/"
            }
        }
        stage('Install Dependencies') {
            agent {
                docker 'maven:3-alpine'
            }
            steps {
                dir(traineeCode) {
                    echo 'Install dependencies'
                    sh 'mvn clean dependency:resolve'
                }
            }
        }
        stage('Build') {
            agent {
                docker 'maven:3-alpine'
            }
            steps {
                dir(traineeCode) {
                    echo 'Build code'
                    sh 'mvn package'
                }
            }
        }
        stage('Test') {
            agent {
                docker 'maven:3-alpine'
            }
            steps {
                dir(traineeCode) {
                    echo 'Run unit test'
                    sh 'mvn test || true'
                }
            }
            post {
                always {
                    dir(traineeCode) {
                        echo 'Record JUnit Result'
                        junit 'target/surefire-reports/*.xml'
                    }
                }
            }
        }
        stage('Lint') {
            agent {
                docker 'maven:3-alpine'
            }
            steps {
                dir(traineeCode) {
                    echo 'Run Lint Tools'
                    sh 'mvn checkstyle:checkstyle || true'
                }
            }
            post {
                always {
                    dir(traineeCode) {
                        echo 'Record Checkstyle Result'
                        recordIssues(tool: checkStyle(pattern: 'target/checkstyle-result.xml'))
                    }
                }
            }
        }
        stage('Report') {
            agent any
            steps {
                fass(traineeAccount: "${traineeAccount}", exercise: "${exercise.minus(traineeAccount + '/' + traineeCode)}")
                emailext(body: '''${SCRIPT, template="report-email-02.gsp"}''',
                        subject: "[Fresher Academy] Your work report",
                        to: "${traineeEmail}",
                        replyTo: "${traineeEmail}",
                        recipientProviders: [[$class: 'CulpritsRecipientProvider']])
            }
        }
        stage('Clean up') {
            agent any
            steps {
                echo 'Clean up'
            }
        }
    }
}

