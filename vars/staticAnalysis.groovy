def call(Map params = [:]) {
    boolean shouldAbort = params.get('shouldAbort', false)
    boolean performQualityCheck = params.get('performQualityCheck', false)
    def currentBranch = env.BRANCH_NAME

    withSonarQubeEnv('SonarQube Local') {
        sh 'echo "Iniciando análisis de calidad de código..."'
        
        timeout(time: 5, unit: 'MINUTES') {
            echo 'Esperando resultados del análisis...'           

            if (performQualityCheck) {
                 // Simulación de una llamada a un análisis de calidad
                def qualityResult = 'PASSED' 
                echo "Estado del Quality Gate: ${qualityResult}"
               
                if (shouldAbort) {
                        error 'El análisis ha fallado; el pipeline se aborta.'
                } else if (currentBranch == 'main' || currentBranch.startsWith('bugfix')) {
                        error "El Quality Gate ha fallado en la rama '${currentBranch}', abortando el pipeline."
                    } else {
                        echo "El Quality Gate ha fallado, pero se continúa en la rama '${currentBranch}'."
                    }
               
            }
        }
    }
}



