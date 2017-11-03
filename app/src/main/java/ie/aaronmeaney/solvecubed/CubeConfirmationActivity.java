package ie.aaronmeaney.solvecubed;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Allows the user to confirm their cube configuration from the CubeInputActivity.
 */
public class CubeConfirmationActivity extends SolveCubedAppCompatActivity {

    // Buttons
    private Button btnCancel;
    private Button btnConfirm;
    private ImageButton btnCubeRotateLeft;
    private ImageButton btnCubeRotateRight;
    private Button btnCubeInvert;

    // Cube outline image
    private ImageView cubeMainImage;

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

        // Inflate the view
        setContentView(R.layout.activity_cube_confirmation);

        /*
         * Post view inflation
         */

        // Get references to button UI elements
        btnCancel = findViewById(R.id.cube_confirmation_btn_cancel);
        btnConfirm = findViewById(R.id.cube_confirmation_btn_confirm);
        btnCubeRotateLeft = findViewById(R.id.cube_confirmation_btn_cube_left);
        btnCubeRotateRight = findViewById(R.id.cube_confirmation_btn_cube_right);
        btnCubeInvert = findViewById(R.id.cube_confirmation_btn_cube_invert);

        // Get references to image UI elements
        //cubeMainImage = findViewById(R.id.cube_confirmation_image_cube_main);

        // Set the main image tag to know if it's inverted or not
        //cubeMainImage.setTag(R.drawable.ic_rubiks_main);

        // Setup button onClick listeners
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Not done yet");
            }
        });

        btnCubeRotateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Not done yet");
            }
        });

        btnCubeRotateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Not done yet");
            }
        });

        btnCubeInvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invertCube();
            }
        });
    }

    /**
     * Inverts the visual representation of the cube
     */
    private void invertCube() {
        // Set the main image
        /*
        switch ((int)cubeMainImage.getTag()) {
            case R.drawable.ic_rubiks_main:
                cubeMainImage.setImageResource(R.drawable.ic_rubiks_main_inverted);
                cubeMainImage.setTag(R.drawable.ic_rubiks_main_inverted);
                break;
            case R.drawable.ic_rubiks_main_inverted:
                cubeMainImage.setImageResource(R.drawable.ic_rubiks_main);
                cubeMainImage.setTag(R.drawable.ic_rubiks_main);
                break;
            default:
                cubeMainImage.setImageResource(R.drawable.ic_rubiks_main);
                cubeMainImage.setTag(R.drawable.ic_rubiks_main);
                break;
        }
        */
    }
}
