$ErrorActionPreference = 'Continue'

$urls = @(
    'https://docs.oracle.com/javase/specs/jls/se21/html/jls-7.html',
    'https://docs.oracle.com/javase/tutorial/java/package/index.html',
    'https://openjdk.org/jeps/0',
    'https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Package.html'
)

foreach ($url in $urls) {
    try {
        $response = Invoke-WebRequest -Uri $url -Method Head -UseBasicParsing -TimeoutSec 20
        Write-Host "OK $($response.StatusCode): $url" -ForegroundColor Green
    }
    catch {
        Write-Host "WARN: nie udało się zweryfikować $url" -ForegroundColor Yellow
    }
}

