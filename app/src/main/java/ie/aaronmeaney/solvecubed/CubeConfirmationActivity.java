package ie.aaronmeaney.solvecubed;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ie.aaronmeaney.rubikscube.RubiksCube;
import ie.aaronmeaney.rubikscube.RubiksFace;
import ie.aaronmeaney.utils.IntentUtilities;

/**
 * Allows the user to confirm their cube configuration from the CubeInputActivity.
 */
public class CubeConfirmationActivity extends SolveCubedAppCompatActivity {

    // Buttons
    private Button btnCancel;
    private Button btnConfirm;

    // Rubik's Cube
    private RubiksCube rubiksCube;

    // UI Elements
    private LinearLayout cubeFaceTop;
    private LinearLayout cubeFaceLeft;
    private LinearLayout cubeFaceBottom;
    private LinearLayout cubeFaceFront;
    private LinearLayout cubeFaceRight;
    private LinearLayout cubeFaceBack;

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

        setHelpDialogue(getResources().getString(R.string.cube_confirmation_help));

        // Inflate the view
        setContentView(R.layout.activity_cube_confirmation);

        /*
         * Post view inflation
         */

        // Get references to button UI elements
        btnCancel = findViewById(R.id.cube_confirmation_btn_cancel);
        btnConfirm = findViewById(R.id.cube_confirmation_btn_confirm);

        // Update the cube colours
        rubiksCube = IntentUtilities.GetExtraRubiksCube(getIntent(), getResources().getString(R.string.cube_input_cube_data));
        updateCubeRender(rubiksCube);

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
                IntentUtilities.StartActivity(thisActivity, CubeSolverActivity.class);
            }
        });
    }

    /**
     * Updates the rendering of the Rubik's Cube view
     * @param cube The Rubik's Cube to copy the color values from
     */
    private void updateCubeRender(RubiksCube cube) {

        for (RubiksFace face : cube.getAllFaces()) {
            LinearLayout root = null;

            switch (face.getFacePosition()) {
                case RIGHT:
                    root = findViewById(R.id.cube_confirmation_layout_face_right);
                    break;
                case BACK:
                    root = findViewById(R.id.cube_confirmation_layout_face_back);
                    break;
                case LEFT:
                    root = findViewById(R.id.cube_confirmation_layout_face_left);
                    break;
                case FRONT:
                    root = findViewById(R.id.cube_confirmation_layout_face_front);
                    break;
                case UP:
                    root = findViewById(R.id.cube_confirmation_layout_face_top);
                    break;
                case DOWN:
                    root = findViewById(R.id.cube_confirmation_layout_face_bottom);
                    break;
            }

            updateCubeFace(face, root);
        }
    }

    /**
     * Updates the face of the layout with the stored color data.
     * @param face The RubiksFace that contains the stored color data.
     * @param layoutRoot The root of the LinearLayout to update the colors of.
     */
    private void updateCubeFace(RubiksFace face, LinearLayout layoutRoot) {
        LinearLayout rowOne = (LinearLayout)layoutRoot.getChildAt(0);
        LinearLayout rowTwo = (LinearLayout)layoutRoot.getChildAt(1);
        LinearLayout rowThree = (LinearLayout)layoutRoot.getChildAt(2);

        ImageView topLeft = (ImageView)rowOne.getChildAt(0);
        ImageView top = (ImageView)rowOne.getChildAt(1);
        ImageView topRight = (ImageView)rowOne.getChildAt(2);
        ImageView left = (ImageView)rowTwo.getChildAt(0);
        ImageView center = (ImageView)rowTwo.getChildAt(1);
        ImageView right = (ImageView)rowTwo.getChildAt(2);
        ImageView bottomLeft = (ImageView)rowThree.getChildAt(0);
        ImageView bottom = (ImageView)rowThree.getChildAt(1);
        ImageView bottomRight = (ImageView)rowThree.getChildAt(2);

        topLeft.setColorFilter(face.getSquareAsColor(1,1));
        top.setColorFilter(face.getSquareAsColor(2,1));
        topRight.setColorFilter(face.getSquareAsColor(3,1));
        left.setColorFilter(face.getSquareAsColor(1,2));
        center.setColorFilter(face.getSquareAsColor(2,2));
        right.setColorFilter(face.getSquareAsColor(3,2));
        bottomLeft.setColorFilter(face.getSquareAsColor(1,3));
        bottom.setColorFilter(face.getSquareAsColor(2,3));
        bottomRight.setColorFilter(face.getSquareAsColor(3,3));
    }
}
