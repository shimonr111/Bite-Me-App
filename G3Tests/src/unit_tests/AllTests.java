package unit_tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ Login_Test.class, LoginQueries_Test.class, ReportsController_Test.class,
		ViewSystemReportsScreenController_Test.class })
public class AllTests {

}
