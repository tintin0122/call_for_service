pipelineJob('Call_For_Service') {

    def repo = 'https://github.com/tintin0122/call_for_service.git'

    triggers {
        scm('*/15 * * * *')
    }

    definition {
        cpsScm {
            scm {
                git(repo, 'main', { node -> node / 'extensions' << '' } )
            }
        }
    }
}