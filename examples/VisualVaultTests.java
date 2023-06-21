

public class VisualVaultTests
{
    public static void main(String[] args) throws Exception {

        var examples = new UserLoginTokenExamples();

        var loginToken = examples.GetUserLoginToken(args);

        System.out.println(" user login token = " + loginToken);
    }
    
}
