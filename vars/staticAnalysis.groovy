def call(Map params = [:]) {
     boolean abortPipeline = params.get('abortPipeline', false)
    boolean qualityGateCheck = params.get('qualityGateCheck', false)

    withSonarQubeEnv('SonarQube Local') {
        sh 'echo "Iniciando análisis de calidad de código..."'
        
        timeout(time: 5, unit: 'MINUTES') {
            echo 'Esperando resultados del análisis...'           

            if (qualityGateCheck) {
                 // Simulación de una llamada a un análisis de calidad
                def qualityResult = 'PASSED' 
                echo "Estado del Quality Gate: ${qualityResult}"
               
               if(qualityResult == 'FAILED' && abortPipeline){
                error 'El análisis ha fallado y se ha abortado el pipeline.'
               }
            }
        }
    }
}



