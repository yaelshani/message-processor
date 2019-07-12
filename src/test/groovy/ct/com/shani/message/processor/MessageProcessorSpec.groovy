package ct.com.shani.message.processor

import spock.lang.Shared
import spock.lang.Specification

class MessageProcessorSpec  extends Specification {

    public static final String STATUS_NOT_FOUND = "Not-Found"
    public static final String ACCEPTED = "Accepted"
    public static final String COMPLETE = "Complete"

    @Shared
    AppDriver app = new AppDriver()

    def setupSpec() {
        app.start()
    }

    def cleanupSpec() {
        app.stop()
    }

    def "should receive status not found when message id does not exist"() {
        when:
        def status = app.getStatus("invalid-message-id")

        then:
        assert status == STATUS_NOT_FOUND
    }

    def "should receive message id when submitting message"() {
        when:
        String messageId = app.sendMessage("What's up doc?")

        then:
        assert app.getStatus(messageId) == COMPLETE
    }


}
