pipeline {
    agent any
    tools {
        maven 'Maven'
        nodejs 'NodeJS'
    }
    stages {
        stage('Compiler') {
            steps {
                sh 'ls -la && cd GoSecuriApp && mvn --version && mvn clean && mvn package && mvn install && java -jar target/GoSecuriApp-1.0-SNAPSHOT.jar'
            }
        }
        stage('Run') {
            steps {
                sh 'ls -la && cd GoSecuriApp && java -jar target/GoSecuriApp-1.0-SNAPSHOT.jar'
            }
        }
        stage('Get JWT Token') {
            steps {
            script {
                withCredentials([usernamePassword(credentialsId: 'Portainer', usernameVariable: 'sk4m', passwordVariable: 'Yakuza89!Alaplage')]) {
                    def json = """
                        {"Username": "$PORTAINER_USERNAME", "Password": "$PORTAINER_PASSWORD"}
                    """
                    def jwtResponse = httpRequest acceptType: 'APPLICATION_JSON', contentType: 'APPLICATION_JSON', validResponseCodes: '200', httpMode: 'POST', ignoreSslErrors: true, consoleLogResponseBody: true, requestBody: json, url: "https://portainer.<yourdomain>.com/api/auth"
                    def jwtObject = new groovy.json.JsonSlurper().parseText(jwtResponse.getContent())
                    env.JWTTOKEN = "Bearer ${jwtObject.jwt}"
                }
            }
            echo "${env.JWTTOKEN}"
            }
        }
        stage('Build Docker Image on Portainer') {
            steps {
            script {
                // Build the image
                withCredentials([usernamePassword(credentialsId: 'Github', usernameVariable: 'LoicBrison')]) {
                    def repoURL = """
                    https://manage-portainer.sk4m.fr/api/endpoints/1/docker/build?t=GoSecuri:latest&remote=https://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@github.com/$GITHUB_USERNAME/GoSecuri.git&dockerfile=Dockerfile&nocache=true
                    """
                    def imageResponse = httpRequest httpMode: 'POST', ignoreSslErrors: true, url: repoURL, validResponseCodes: '200', customHeaders:[[name:"Authorization", value: env.JWTTOKEN ], [name: "cache-control", value: "no-cache"]]
                }
            }
            }
        }
        stage('Delete old Stack') {
            steps {
            script {

                // Get all stacks
                String existingStackId = ""
                if("true") {
                def stackResponse = httpRequest httpMode: 'GET', ignoreSslErrors: true, url: "https://manage-portainer.sk4m.fr/api/stacks", validResponseCodes: '200', consoleLogResponseBody: true, customHeaders:[[name:"Authorization", value: env.JWTTOKEN ], [name: "cache-control", value: "no-cache"]]
                def stacks = new groovy.json.JsonSlurper().parseText(stackResponse.getContent())
                
                stacks.each { stack ->
                    if(stack.Name == "GOSECURI") {
                    existingStackId = stack.Id
                    }
                }
                }

                if(existingStackId?.trim()) {
                // Delete the stack
                def stackURL = """
                    https://manage-portainer.sk4m.fr/api/stacks/$existingStackId
                """
                httpRequest acceptType: 'APPLICATION_JSON', validResponseCodes: '204', httpMode: 'DELETE', ignoreSslErrors: true, url: stackURL, customHeaders:[[name:"Authorization", value: env.JWTTOKEN ], [name: "cache-control", value: "no-cache"]]

                }

            }
            }
        }
        stage('Deploy new stack to Portainer') {
            steps {
                script {
                    def createStackJson = ""

                    // Stack does not exist
                    // Generate JSON for when the stack is created
                    withCredentials([usernamePassword(credentialsId: 'Github', usernameVariable: 'LoicBrison', passwordVariable: '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')]) {
                        def swarmResponse = httpRequest acceptType: 'APPLICATION_JSON', validResponseCodes: '200', httpMode: 'GET', ignoreSslErrors: true, consoleLogResponseBody: true, url: "https://manage-portainer.sk4m.fr/api/endpoints/1/docker/swarm", customHeaders:[[name:"Authorization", value: env.JWTTOKEN ], [name: "cache-control", value: "no-cache"]]
                        def swarmInfo = new groovy.json.JsonSlurper().parseText(swarmResponse.getContent())

                        createStackJson = """
                        {"Name": "GOSECURI", "SwarmID": "$swarmInfo.ID", "RepositoryURL": "https://github.com/$GITHUB_USERNAME/GoSecuri", "ComposeFilePathInRepository": "docker-compose.yml", "RepositoryAuthentication": true, "RepositoryUsername": "$GITHUB_USERNAME", "RepositoryPassword": "$GITHUB_PASSWORD"}
                        """

                    }

                    if(createStackJson?.trim()) {
                        httpRequest acceptType: 'APPLICATION_JSON', contentType: 'APPLICATION_JSON', validResponseCodes: '200', httpMode: 'POST', ignoreSslErrors: true, consoleLogResponseBody: true, requestBody: createStackJson, url: "https://manage-portainer.sk4m.fr/api/stacks?method=repository&type=1&endpointId=1", customHeaders:[[name:"Authorization", value: env.JWTTOKEN ], [name: "cache-control", value: "no-cache"]]
                    }
                }
            }
        }
    }
}
