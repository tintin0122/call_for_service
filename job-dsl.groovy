pipelineJob('Call-For-Service') {

    def repo = 'https://github.com/tintin0122/call_for_service.git'

    definition {
        cpsScm {
            scm {
                git(repo, 'main', { node -> node / 'extensions' << '' } )
            }
        }
    }
}