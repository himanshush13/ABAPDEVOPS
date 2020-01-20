pipeline {
   agent any
   stages{
         stage('ABAP Unit Test'){
               steps{
                     withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: '', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                     sh '''
                     newman run https://github.com/himanshush13/ABAPDEVOPS/master/01_ABAP_Unit/abap_unit.postman_collection.json -k --bail --environment https://github.com/mygit999/ABAPDevOps.git/ABAPDEVOPS/master/01_ABAP_Unit/abap_unit.postman_environment.json -k --timeout-request 120000 --global-var "username=$USERNAME" --global-var "password=$PASSWORD" --global-var "package=zdevops" 
                     '''
                     }
                }
         }
        stage('ABAP Test Coverage'){
               steps{
                    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: '', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                    sh ''' 
                    newman run https://github.com/himanshush13/ABAPDEVOPS/master/02_ABAP_Coverage/abap_coverage_analysis.postman_collection.json -k --bail --environment https://github.com/mygit999/ABAPDevOps.git/ABAP_CI_PIPELINE_BASE/master/02_ABAP_Coverage/abap_coverage.postman_environment.json --timeout-request 120000 --global-var "username=$USERNAME" --global-var "password=$PASSWORD" --global-var "package=zdevops" 
                    '''
                    }
               }
         }
        
             
         stage('ATC Checks DEFAULT'){
                        steps{
                            withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: '', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                            sh ''' 
                           newman run https://github.com/himanshush13/ABAPDEVOPS/master/03_ATC_Checks/abap_atc.postman_collection.json -k --bail --environment https://github.com/mygit999/ABAPDevOps.git/ABAPDEVOPS/master/03_ATC_Checks/abap_atc_funcdb.postman_environment.json --timeout-request 120000 --global-var "username=$USERNAME" --global-var "password=$PASSWORD" --global-var "package=ZDEVOPS"
                           '''
                           }
                        }
                     }
                     
             
               
   }
}
