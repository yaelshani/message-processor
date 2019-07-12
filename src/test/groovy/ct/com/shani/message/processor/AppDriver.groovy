package ct.com.shani.message.processor

import com.shani.message.processor.Application
import groovy.json.JsonOutput
import groovyx.net.http.RESTClient
import org.springframework.boot.SpringApplication

class AppDriver {

    def service

    RESTClient client = new RESTClient('http://localhost:8080')

    def start() {
        service = SpringApplication.run(Application.class)
    }

    def stop() {
        service.close()
    }

    String getStatus(String messageId) {
        def response = client.get(path: "/v1/messages/${messageId}/status",
                contentType: "application/json"
        )
        assert response.status == 200
        return response.data.status
    }

    String sendMessage(String message) {
        def response = client.post(path: "/v1/messages/",
                body: JsonOutput.toJson(message),
                contentType: "application/json"
        )
        assert response.status == 202
        return response.data.messageId
    }
}
