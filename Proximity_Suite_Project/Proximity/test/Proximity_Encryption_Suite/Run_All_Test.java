package Proximity_Encryption_Suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    Device_Monitoring_ThreadIT.class,
    Encryption_AESIT.class,
    Encryption_DESIT.class,
    Encryption_Triple_DESIT.class,
    File_Loading_ThreadIT.class,
    Files_AddIT.class,
    Files_DecryptionIT.class,
    Files_EncryptionIT.class,
    Files_RemoveIT.class,
    Login_Account_Recover_PasswordIT.class,
    Suite_DatabaseIT.class,
    Suite_WindowIT.class,
    Test_Account.class,
    Test_Device.class,
    Test_Device_Thread.class,
    Test_Main.class,})

public class Run_All_Test {
}

