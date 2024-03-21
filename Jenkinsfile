pipeline {

    agent any

    tools {
        maven '3.9.4'
        jdk '17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }


        stage('Test') {
            steps {
                // Run tests
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                // Package the Spring Boot application
                sh 'mvn package'
            }
        }

        stage('Deploy') {
            steps {
                // Here, you can deploy your application to your desired environment
                // This may involve copying artifacts to a server, deploying to a container, etc.
                // You can use the appropriate deployment tools and scripts for your environment
                sh 'echo "Deploying the application"'
            }
        }
    }

    post {
        success {
            // Perform actions after a successful build and deployment
            // This can include notifications, triggering other jobs, etc.
            echo 'Build and deployment successful'
        }
        failure {
            // Perform actions if the build or deployment fails
            // This can include notifications, cleanup, etc.
            echo 'Build or deployment failed'
        }
    }
}
