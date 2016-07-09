package de.mario.photo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


/**
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageHandlerTest {
    private static final String[] DUMMY = new String[]{"a", "b", "c"};
    @Mock
    private PhotoActivity activity;
    @Mock
    private MessageWrapper wrapper;
    @InjectMocks
    private MessageHandler classUnderTest;


    @Before
    public void setUp() {
        given(activity.getString(anyInt())).willReturn("test");
        given(wrapper.getStringArray(PhotoActivable.PICTURES)).willReturn(DUMMY);
        given(wrapper.getString(PhotoActivable.SAVE_FOLDER)).willReturn("a");
    }

    @Test
    public void testHandleMessage_String() {
        given(wrapper.isDataEmpty()).willReturn(true);
        classUnderTest.handleMessage(wrapper);
        verify(activity).toast(anyString());
    }

    @Test
    public void testHandleMessage_PictureInfo() {
        classUnderTest.handleMessage(wrapper);
        verify(activity, times(DUMMY.length)).refreshPictureFolder(anyString());
    }
}
