package tp.client.graphical;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.swing.JFrame;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GUIManager.class)
@SuppressStaticInitializationFor("javax.swing.*")
public class GUIGeneralTest {

	@Test
	public void ManagerTest() throws Exception {
		//JFrame mainWindow = Mockito.spy(JFrame.class);
		//PowerMockito.whenNew(JFrame.class).withAnyArguments().thenReturn(mainWindow);
		//GUIManager undertest = new GUIManager();
	}
}
