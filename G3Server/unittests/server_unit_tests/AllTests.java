package server_unit_tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LoginQueries_Test.class, ReportsController_Test.class })
public class AllTests {

}
