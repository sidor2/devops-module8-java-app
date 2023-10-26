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

return this
