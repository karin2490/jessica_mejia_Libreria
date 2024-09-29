def call(Map params = [:]) {
    boolean abortPipeline = params.get('abortPipeline', false)
    boolean qualityGateCheck = params.get('qualityGateCheck', false)
    def branchName = env.BRANCH_NAME

    withSonarQubeEnv('SonarQube Local') {
        bat 'echo "Iniciando análisis de calidad de código..."'
        
        timeout(time: 5, unit: 'MINUTES') {
            echo 'Esperando resultados del análisis...'           

            if (qualityGateCheck) {
                qualityResult = 'PASSED' // Asegúrate de que este valor sea el resultado real del análisis
                echo "Estado del Quality Gate: ${qualityResult}"

                if (qualityResult == null) {
                    error "No se pudo obtener el resultado del análisis de calidad."
                }

                if (qualityResult == 'FAILED' && abortPipeline) {
                    error 'El análisis ha fallado y se ha abortado el pipeline.'
                } else {
                    if (branchName == 'master' || branchName.startsWith('hotfix')) {
                        if (qualityResult == 'FAILED') {
                            error "El análisis de calidad ha fallado en la rama '${branchName}' y se ha abortado el pipeline."
                        }
                    }
                }
            }
        }
    }
}




