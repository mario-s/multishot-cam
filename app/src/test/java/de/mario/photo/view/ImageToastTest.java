package de.mario.photo.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.mario.photo.R;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ImageToastTest {

    @Mock
    private Context context;

    @Mock
    private View toastView;

    @Mock
    private ImageView imageView;

    @Mock
    private TextView textView;

    private ImageToast classUnderTest;

    @Before
    public void setUp() {
        given(toastView.findViewById(R.id.toast_image)).willReturn(imageView);
        given(toastView.findViewById(R.id.toast_text)).willReturn(textView);

        classUnderTest = new ImageToast(toastView);
    }

    @Test
    public void testSetText() {
        String text = "test";
        classUnderTest.setText(text);
        verify(textView).setText(text);
    }
}
