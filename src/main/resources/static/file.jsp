@Service
public class OtpService {

    @Value("${twilio.account_sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth_token}")
    private String twilioAuthToken;

    @Value("${twilio.phone_number}")
    private String twilioPhoneNumber;

    public String generateOtp() {
        // Generate a random 6-digit OTP
        Random random = new Random();
        int otp = 100_000 + random.nextInt(900_000);
        return String.valueOf(otp);
    }

    public boolean sendOtp(String phoneNumber, String otp) {
        Twilio.init(twilioAccountSid, twilioAuthToken);
        try {
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(phoneNumber),
                    new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                    "Your OTP is: " + otp
            ).create();
            return message.getStatus().equals(Message.Status.SENT);
        } catch (Exception e) {
            // Handle Twilio API exceptions
            return false;
        }
    }

    public boolean verifyOtp(String phoneNumber, String otp) {
        // Implement your OTP verification logic here
        // Compare the provided OTP with the one sent earlier
        // You may use a data store (e.g., database) to store OTPs and verify them.
        return true; // For simplicity, we always return true here
    }
}
