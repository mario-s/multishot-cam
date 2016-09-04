package de.mario.photo;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.mario.photo.glue.PhotoActivable;
import de.mario.photo.support.MessageWrapper;

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
    private Context context;
    @Mock
    private PhotoActivity activity;
    @Mock
    private MessageWrapper wrapper;

    private MessageHandler classUnderTest;


    @Before
    public void setUp() {

        given(activity.getContext()).willReturn(context);
        given(activity.getString(anyInt())).willReturn("test");
        given(wrapper.getStringArray(PhotoActivable.PICTURES)).willReturn(DUMMY);
        given(wrapper.getString(PhotoActivable.SAVE_FOLDER)).willReturn("a");


        classUnderTest = new MessageHandler(activity) {
            @Override
            void toast(String msg) {
            }
        };
    }

    @Test
    public void testHandleStringMessage_ShouldToast() {
        given(wrapper.isDataEmpty()).willReturn(true);
        given(wrapper.getParcelAsString()).willReturn("foo");
        classUnderTest.handleMessage(wrapper);
        verify(wrapper).getParcelAsString();
    }

    @Test
    public void testHandlePrepareMessage_ShouldPrepare() {
        given(wrapper.isDataEmpty()).willReturn(true);
        given(wrapper.getParcelAsString()).willReturn(PhotoActivable.PREPARE_FOR_NEXT);
        classUnderTest.handleMessage(wrapper);
        verify(activity).prepareForNextShot();
    }

    @Test
    public void testHandleMessage_PictureInfo() {
        classUnderTest.handleMessage(wrapper);
        verify(activity, times(DUMMY.length)).refreshPictureFolder(anyString());
    }
}
