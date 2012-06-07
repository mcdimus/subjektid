package junit.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SubjectsDAOTest.class, SubjectsORMTest.class })
public class AllTests {

}
