function copyEnvVarsToGradleProperties {
    GRADLE_PROPERTIES=$HOME"/.gradle/gradle.properties"
    export GRADLE_PROPERTIES
    echo "Gradle Properties should exist at $GRADLE_PROPERTIES"

    if [ ! -f "$GRADLE_PROPERTIES" ]; then
        echo "Gradle Properties does not exist"

        echo "Creating Gradle Properties file..."
        touch $GRADLE_PROPERTIES

        echo "Writing TEST_API_KEY to gradle.properties..."
        echo "MY_DB_KEY=$MY_DB_KEY_ENV_VAR" >> $GRADLE_PROPERTIES
        echo "MY_GITHUB_USERS_KEY=$MY_GITHUB_USERS_KEY_ENV_VAR" >> $GRADLE_PROPERTIES

    fi
}