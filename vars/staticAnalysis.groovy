def call(boolean abortPipeline = false) {
    timeout(time: 5, unit: 'MINUTES') {
        withSonarQubeEnv('SonarQube') {
            // Simulación del análisis estático de código
            sh 'echo "Ejecución de las pruebas de calidad de código"'
        }

        // Simulación del QualityGate
        def qualityGateResult = waitForQualityGate()

        if (qualityGateResult.status != 'OK' && abortPipeline) {
            error "Quality Gate fallido. Abortando el pipeline."
        } else {
            echo "Quality Gate status: ${qualityGateResult.status}. Continuando el pipeline."
        }
    }
}
