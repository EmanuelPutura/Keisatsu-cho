package ubb.keisatsu.cms.repository.database

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix="spring.datasource")
class DatabaseConfig {
    var url: String = "No url found";
    var user: String = "postgres";
    var password: String = "postgres";
}