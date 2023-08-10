pipeline {
  //any available executor should pick the job to execute
  agent any
  //Set a timeout period in minutes for the Pipeline run, after which Jenkins should abort the Pipeline.
  options {
    timeout(20)
  }
  
triggers {
        pollSCM('H/20 * * * *') // Polls the repository for every 20 minutes
    }
    
  stages {
    stage('Checkout-Code') {
      steps {
        echo "Fetching code"
        checkout(
          scmGit(branches: [
            [name: '*/master']
          ], gitTool: 'Default', extensions: [checkoutOption(5)], userRemoteConfigs: [
            [credentialsId: 'GitHubCreds', url: 'https://github.com/SukantBhatia/Selenium_Automation_PVT.git']
          ])
        )
      } // end of steps
    } //end of stage

    stage('Sonar Analysis') {
      steps {
        withSonarQubeEnv(installationName: 'SonarQube_Analysis', credentialsId: 'sonar-jenkins') {
          script {
            echo "Executing Sonar Analysis"
            def sonarTool = tool 'SonarQubeScanner'
            sh "${sonarTool}/bin/sonar-scanner"
          } // end of script 
        }
      } // end of steps
    } //end of stage

    stage('Build Code') {
      steps {
        echo "Building code"
        sh 'mvn clean'
      } // end of steps
    } //end of stage

    stage('Run Test') {
      steps {
        echo "Testing"
        sh 'mvn test -P Smoke'
      } // end of steps
    } //end of stage
  } //end of stages

  post {
    always {
      publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, includes: '**/extentreports.html', keepAll: false, reportDir: 'Current test results/.', reportFiles: '', reportName: 'Extent Test Reports', reportTitles: '', useWrapperFileDirectly: true])
      testNG failedFails: 1, failedSkips: 2, unstableFails: 3, unstableSkips: 3
      junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
      jacoco(
        execPattern: '**/**.exec',
        classPattern: '**/test-classes',
        sourcePattern: 'src/test/java/com/flipkart/automation/pages,src/test/java/com/flipkart/automation/utils'
      )

      script {
        echo "Executing Sonar Analysis for test cases report"
        def sonarTool = tool 'SonarQubeScanner'
        withSonarQubeEnv(installationName: 'SonarQube_Analysis', credentialsId: 'sonar-jenkins') {
          sh "${sonarTool}/bin/sonar-scanner"
        }
      } //end of script
    }

    success {
      echo "********** BUILD SUCCESS ***********"
      echo "Deploying artifacts to jfrog respository"
      script {
        rtMavenDeployer(
          id: 'deployer',
          serverId: 'nagpassignment@artifactory',
          releaseRepo: 'nagpassignment',
          snapshotRepo: 'nagpassignment'
        )

        rtMavenRun(
          pom: 'pom.xml',
          goals: 'clean install -DskipTests',
          deployerId: 'deployer'
        )

        rtPublishBuildInfo(
          serverId: 'nagpassignment@artifactory'
        )
      } // end of script 
    }

    failure {
      echo "********** BUILD FAILED ***********"
    }
  }
}