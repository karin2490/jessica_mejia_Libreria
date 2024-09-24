def call(Map params = [:]) {
    boolean shouldAbort = params.get('shouldAbort', false)
    boolean performQualityCheck = params.get('performQualityCheck', false)
    def currentBranch = env.BRANCH_NAME

    withSonarQubeEnv('SonarQube Server') {
        sh 'echo "Iniciando análisis de calidad de código..."'
        
        timeout(time: 5, unit: 'MINUTES') {
            echo 'Esperando resultados del análisis...'

            // Simulación de una llamada a un análisis de calidad
            def qualityResult = 'PASSED' 

            if (performQualityCheck) {
                echo "Estado del Quality Gate: ${qualityResult}"

                if (qualityResult == 'FAILED') {
                    if (shouldAbort) {
                        error 'El Quality Gate ha fallado; el pipeline se aborta.'
                    } else if (currentBranch == 'main' || currentBranch.startsWith('bugfix')) {
                        error "El Quality Gate ha fallado en la rama '${currentBranch}', abortando el pipeline."
                    } else {
                        echo "El Quality Gate ha fallado, pero se continúa en la rama '${currentBranch}'."
                    }
                } else {
                    echo "El Quality Gate ha sido aprobado, continuando el pipeline."
                }
            }
        }
    }
}
