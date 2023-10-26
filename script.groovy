def testApp() {
    echo "Testing the application"
}

def deployApp() {
    echo "Deploying the application"
}

def incrementVersion() {
    echo "Incrementing the version"
    sh "mvn build-helper:parse-version versions:set \
        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
        -DnextSnapshot=true versions:commit"
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    def version = matcher[0][1]
    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
}

def clean() {
    echo "Cleaning the workspace"
    sh "mvn clean package"
}

def commitPom() {
    sshagent(['2c40c606-3564-4fc4-8fc2-3a89a016f089']) {
        sh 'git config --global user.email "jenkins@example.com"'
        sh 'git config --global user.name "jenkins"'

        sh 'git status'
        sh 'git branch'
        sh 'git config --list'

        sh "git remote set-url origin https://${USER}:${PASS}@github.com/sidor2/devops-module8-java-app.git"
        sh 'git add .'
        sh 'git commit -m "ci: version bump"'
        sh 'git push origin HEAD:jenkins-shared-lib'
    }
}

return this
