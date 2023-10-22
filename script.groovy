def buildJar() {
    echo "Building the jar file"
    sh "mvn package"
}

def buildImage() {
    echo "Building the docker image"
    withCredentials([usernamePassword(credentialsId: 'devops-token', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t $USER/devops:jma-2.0 ."
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push $USER/devops:jma-2.0"
    }
}

def deployApp() {
    echo "Deploying the application"
}
return this

