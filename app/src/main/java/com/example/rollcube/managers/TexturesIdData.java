package com.example.rollcube.managers;

import static android.opengl.GLES20.glDeleteTextures;

import android.content.Context;

import com.example.rollcube.GameBoard;
import com.example.rollcube.R;
import com.example.rollcube.TextureUtils;

/**
 * Class contains all texture IDs.
 */
public class TexturesIdData {
    private Context context;

    //////////////////////////////////////////////////////////////////////
    ///////////////region    Backgrounds
    public int background_starting;
    public int background_figures;
    public int background_dice;
    public int background_XYZ;
    //endregion


    //////////////////////////////////////////////////////////////////////
    ///////////////region    Timer
    public int timer_background;
    public int time_records_background;

    public int timer_number_0;
    public int timer_number_1;
    public int timer_number_2;
    public int timer_number_3;
    public int timer_number_4;
    public int timer_number_5;
    public int timer_number_6;
    public int timer_number_7;
    public int timer_number_8;
    public int timer_number_9;

    public int timer_dash;
    public int timer_point;
    public int timer_colon;
    //endregion


    //////////////////////////////////////////////////////////////////////
    /////////////////region   Common Textures
    public int wall_side;
    public int tr_wall_side;
    public int gap_cap;
    public int tr_wall_cap;
    public int platform_side;
    public int board_side;
    //endregion


    //////////////////////////////////////////////////////////////////////
    ///////////////region    Tile Figures
    public int tile_figures_empty;

    public int tile_circle;
    public int tile_square;
    public int tile_four_circles;
    public int tile_four_crosses;
    public int tile_cross;
    public int tile_four_squares;

    public int tile_tr_circle;
    public int tile_tr_square;
    public int tile_tr_four_circles;
    public int tile_tr_four_crosses;
    public int tile_tr_cross;
    public int tile_tr_four_squares;
    //endregion


    //////////////////////////////////////////////////////////////////////
    ///////////////region    Tile Dice
    public int tile_dice_empty;
    public int tile_numb_1;
    public int tile_numb_2x;
    public int tile_numb_2y;
    public int tile_numb_3xy;
    public int tile_numb_3xy2;
    public int tile_numb_4;
    public int tile_numb_5;
    public int tile_numb_6x;
    public int tile_numb_6y;

    public int tile_tr_numb_1;
    public int tile_tr_numb_2x;
    public int tile_tr_numb_2y;
    public int tile_tr_numb_3xy;
    public int tile_tr_numb_3xy2;
    public int tile_tr_numb_4;
    public int tile_tr_numb_5;
    public int tile_tr_numb_6x;
    public int tile_tr_numb_6y;
    //endregion


    //////////////////////////////////////////////////////////////////////
    ///////////////region    Tile XYZ
    public int tile_XYZ_empty;

    public int tile_XYZ_X;
    public int tile_XYZ_Y1;
    public int tile_XYZ_Y2;
    public int tile_XYZ_Y3;
    public int tile_XYZ_Y4;
    public int tile_XYZ_Z1;
    public int tile_XYZ_Z2;
    public int tile_XYZ_Z3;
    public int tile_XYZ_Z4;

    public int tile_tr_XYZ_X;
    public int tile_tr_XYZ_Y1;
    public int tile_tr_XYZ_Y2;
    public int tile_tr_XYZ_Y3;
    public int tile_tr_XYZ_Y4;
    public int tile_tr_XYZ_Z1;
    public int tile_tr_XYZ_Z2;
    public int tile_tr_XYZ_Z3;
    public int tile_tr_XYZ_Z4;
    //endregion


    //////////////////////////////////////////////////////////////////////
    ///////////////region    Point Counter
    public int point_counter_background;
    public int point_records_background;

    public int point_counter_number_0;
    public int point_counter_number_1;
    public int point_counter_number_2;
    public int point_counter_number_3;
    public int point_counter_number_4;
    public int point_counter_number_5;
    public int point_counter_number_6;
    public int point_counter_number_7;
    public int point_counter_number_8;
    public int point_counter_number_9;
    public int point_counter_dash;
    //endregion


    //////////////////////////////////////////////////////////////////////
    ///////////////region    Play Button
    public int button_play_active;
    public int button_play_unactive;
    public int button_start;
    public int button_resume;
    public int button_pause;
    public int button_finish;
    //endregion


    //////////////////////////////////////////////////////////////////////
    ///////////////region    Platforms
    public int platform_dice_left_90;
    public int platform_dice_right_90;
    public int platform_dice_left_180;
    public int platform_dice_right_180;

    public int platform_figures_left_90;
    public int platform_figures_right_90;
    public int platform_figures_left_180;
    public int platform_figures_right_180;

    public int platform_xyz_left_90;
    public int platform_xyz_right_90;
    public int platform_xyz_left_180;
    public int platform_xyz_right_180;
    //endregion


    /////////////////////////////////////////////////////////////////////////////
    ////////////////region    Main Menu
    public int menu_background;

    public int menu_button_open_menu;
    public int menu_button_closed_menu;
    public int menu_button_restart;

    public int main_menu_button_new_game;
    public int main_menu_button_records;
    public int main_menu_button_exit;
    public int main_menu_button_settings;
    public int main_menu_button_info;

    public int game_type_scroll_menu_background;
    public int game_type_scroll_menu_figures3_foc;
    public int game_type_scroll_menu_figures3_unf;
    public int game_type_scroll_menu_figures6_foc;
    public int game_type_scroll_menu_figures6_unf;
    public int game_type_scroll_menu_dice_foc;
    public int game_type_scroll_menu_dice_unf;
    public int game_type_scroll_menu_XYZ_foc;
    public int game_type_scroll_menu_XYZ_unf;

    public int menu_button_time_race_foc;
    public int menu_button_time_race_unf;
    public int menu_button_point_race_foc;
    public int menu_button_point_race_unf;

    public int button_point_race_50points_foc;
    public int button_point_race_50points_unf;
    public int button_point_race_150points_foc;
    public int button_point_race_150points_unf;
    public int button_point_race_500points_foc;
    public int button_point_race_500points_unf;

    public int button_time_race_2min_foc;
    public int button_time_race_2min_unf;
    public int button_time_race_5min_foc;
    public int button_time_race_5min_unf;
    public int button_time_race_10min_foc;
    public int button_time_race_10min_unf;

    public int record_picture_gold_place;
    public int record_picture_silver_place;
    public int record_picture_bronze_place;
    public int record_picture_iron_place;

    public int main_menu_button_back;
    public int main_menu_button_save;
    public int main_menu_button_next;
    public int main_menu_button_yes;
    public int main_menu_button_no;

    public int common_font_digit_0;
    public int common_font_digit_1;
    public int common_font_digit_2;
    public int common_font_digit_3;
    public int common_font_digit_4;
    public int common_font_digit_5;
    public int common_font_digit_6;
    public int common_font_digit_7;
    public int common_font_digit_8;
    public int common_font_digit_9;
    public int common_font_point;
    public int common_font_plus;
    public int common_font_minus;

    public int settings_interface_sizes;
    public int settings_main_menu;
    public int settings_button_to_main_menu;
    public int settings_timer_and_pointer;
    public int settings_start_button;
    public int settings_camera_zoom;


    public int info_interacting_with_number;
    public int info_reset_settings;
    public int info_contacts;

    public int ok_board_warning_pic;
    public int ok_board_piece_green1;
    public int ok_board_piece_green2;
    public int ok_board_piece_green3;
    public int ok_board_piece_grey1;
    public int ok_board_piece_grey2;
    public int ok_board_piece_grey3;

    //endregion


    /////////////////////////////////////////////////////////////////////////////
    //////////////region    Cube Figures6
    public int cube_figures_side_1;
    public int cube_figures_side_2;
    public int cube_figures_side_3;
    public int cube_figures_side_4;
    public int cube_figures_side_5;
    public int cube_figures_side_6;
    //endregion


    //////////////////////////////////////////////////////////////////////////////////
    /////////////region   Cube Dice
    public int cube_dice_side_1;
    public int cube_dice_side_2;
    public int cube_dice_side_3;
    public int cube_dice_side_4;
    public int cube_dice_side_5;
    public int cube_dice_side_6;
    //endregion


    //////////////////////////////////////////////////////////////////////////////////
    /////////////region   Cube XYZ
    public int cube_XYZ_X;
    public int cube_XYZ_Y;
    public int cube_XYZ_Z2;
    public int cube_XYZ_Z;
    //endregion


    ///////////////////////////////////////////////////////////////////////////////
    ////////   Board Static
    public int stab;



    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------


    public TexturesIdData(Context context) {
        this.context = context;
        uploadMainTextures(context);
        uploadTexturesFigures(context);
        uploadTexturesDice(context);
        uploadTexturesXYZ(context);
    }


    public void uploadMainTextures(Context context) {
        tile_dice_empty = TextureUtils.loadTexture(context, R.drawable.tile_dice_empty_png);

        background_starting = TextureUtils.loadTexture(context, R.drawable.background_starting_2056x2056_png);

        timer_background = TextureUtils.loadTexture(context, R.drawable.timer_background_540x200_png);
        time_records_background = TextureUtils.loadTexture(context, R.drawable.time_records_background_540x200_png);

        timer_number_0 = TextureUtils.loadTexture(context, R.drawable.timer_number_0_png);
        timer_number_1 = TextureUtils.loadTexture(context, R.drawable.timer_number_1_png);
        timer_number_2 = TextureUtils.loadTexture(context, R.drawable.timer_number_2_png);
        timer_number_3 = TextureUtils.loadTexture(context, R.drawable.timer_number_3_png);
        timer_number_4 = TextureUtils.loadTexture(context, R.drawable.timer_number_4_png);
        timer_number_5 = TextureUtils.loadTexture(context, R.drawable.timer_number_5_png);
        timer_number_6 = TextureUtils.loadTexture(context, R.drawable.timer_number_6_png);
        timer_number_7 = TextureUtils.loadTexture(context, R.drawable.timer_number_7_png);
        timer_number_8 = TextureUtils.loadTexture(context, R.drawable.timer_number_8_png);
        timer_number_9 = TextureUtils.loadTexture(context, R.drawable.timer_number_9_png);

        timer_dash = TextureUtils.loadTexture(context, R.drawable.timer_number_dash_png);
        timer_point = TextureUtils.loadTexture(context, R.drawable.timer_point_png);
        timer_colon = TextureUtils.loadTexture(context, R.drawable.timer_colon_png);


        point_counter_background = TextureUtils.loadTexture(context, R.drawable.point_counter_background_tr_500x200_png);
        point_records_background = TextureUtils.loadTexture(context, R.drawable.point_records_background_500x200_png);

        point_counter_number_0 = TextureUtils.loadTexture(context, R.drawable.point_counter_digit_0_png);
        point_counter_number_1 = TextureUtils.loadTexture(context, R.drawable.point_counter_digit_1_png);
        point_counter_number_2 = TextureUtils.loadTexture(context, R.drawable.point_counter_digit_2_png);
        point_counter_number_3 = TextureUtils.loadTexture(context, R.drawable.point_counter_digit_3_png);
        point_counter_number_4 = TextureUtils.loadTexture(context, R.drawable.point_counter_digit_4_png);
        point_counter_number_5 = TextureUtils.loadTexture(context, R.drawable.point_counter_digit_5_png);
        point_counter_number_6 = TextureUtils.loadTexture(context, R.drawable.point_counter_digit_6_png);
        point_counter_number_7 = TextureUtils.loadTexture(context, R.drawable.point_counter_digit_7_png);
        point_counter_number_8 = TextureUtils.loadTexture(context, R.drawable.point_counter_digit_8_png);
        point_counter_number_9 = TextureUtils.loadTexture(context, R.drawable.point_counter_digit_9_png);
        point_counter_dash = TextureUtils.loadTexture(context, R.drawable.point_counter_dash_png);


        button_play_active = TextureUtils.loadTexture(context, R.drawable.button_play_active_650x256_png);
        button_play_unactive = TextureUtils.loadTexture(context, R.drawable.button_play_unactive_650x256_png);

        button_start = TextureUtils.loadTexture(context, R.drawable.button_start_240x200_png);
        button_resume = TextureUtils.loadTexture(context, R.drawable.button_resume_240x200_png);
        button_pause = TextureUtils.loadTexture(context, R.drawable.button_pause_240x200_png);
        button_finish = TextureUtils.loadTexture(context, R.drawable.button_finish_240x200_png);


        platform_side = TextureUtils.loadTexture(context, R.drawable.platform_side_250x100_png);


        menu_background = TextureUtils.loadTexture(context, R.drawable.background_1024x1024_png);

        menu_button_open_menu = TextureUtils.loadTexture(context, R.drawable.button_menu_open_200x137_png);
        menu_button_closed_menu = TextureUtils.loadTexture(context, R.drawable.button_menu_closed_200x137_png);
        menu_button_restart = TextureUtils.loadTexture(context, R.drawable.button_restart_200x150_png);


        main_menu_button_new_game = TextureUtils.loadTexture(context, R.drawable.button_new_game_1000x566_reverse);
        main_menu_button_records = TextureUtils.loadTexture(context, R.drawable.button_records_480x566_png);
        main_menu_button_exit = TextureUtils.loadTexture(context, R.drawable.button_exit_480x480_png);
        main_menu_button_settings = TextureUtils.loadTexture(context, R.drawable.button_settings_480x480_png);
        main_menu_button_info = TextureUtils.loadTexture(context, R.drawable.button_info_480x480_png);

        main_menu_button_back = TextureUtils.loadTexture(context, R.drawable.button_back_256x256_png);
        main_menu_button_save = TextureUtils.loadTexture(context, R.drawable.button_save_256x256_png);
        main_menu_button_next = TextureUtils.loadTexture(context, R.drawable.button_next_250x200_png);
        main_menu_button_yes = TextureUtils.loadTexture(context, R.drawable.button_yes_256x256_png);
        main_menu_button_no = TextureUtils.loadTexture(context, R.drawable.button_no_256x256_png);


        game_type_scroll_menu_background = TextureUtils.loadTexture(context, R.drawable.game_type_scroll_menu_background_png);
        game_type_scroll_menu_figures3_foc = TextureUtils.loadTexture(context, R.drawable.game_type_scroll_menu_figures3_foc_png);
        game_type_scroll_menu_figures3_unf = TextureUtils.loadTexture(context, R.drawable.game_type_scroll_menu_figures3_unf_png);
        game_type_scroll_menu_figures6_foc = TextureUtils.loadTexture(context, R.drawable.game_type_scroll_menu_figures6_foc_png);
        game_type_scroll_menu_figures6_unf = TextureUtils.loadTexture(context, R.drawable.game_type_scroll_menu_figures6_unf_png);
        game_type_scroll_menu_dice_foc = TextureUtils.loadTexture(context, R.drawable.game_type_scroll_menu_dice_foc_png);
        game_type_scroll_menu_dice_unf = TextureUtils.loadTexture(context, R.drawable.game_type_scroll_menu_dice_unf_png);
        game_type_scroll_menu_XYZ_foc = TextureUtils.loadTexture(context, R.drawable.game_type_scroll_menu_xyz_foc_png);
        game_type_scroll_menu_XYZ_unf = TextureUtils.loadTexture(context, R.drawable.game_type_scroll_menu_xyz_unf_png);

        menu_button_time_race_foc = TextureUtils.loadTexture(context, R.drawable.button_time_race_874x512_foc_png);
        menu_button_time_race_unf = TextureUtils.loadTexture(context, R.drawable.button_time_race_874x512_unf_png);
        menu_button_point_race_foc = TextureUtils.loadTexture(context, R.drawable.button_point_race_foc_874x512_png);
        menu_button_point_race_unf = TextureUtils.loadTexture(context, R.drawable.button_point_race_unf_874x512_png);

        button_point_race_50points_foc = TextureUtils.loadTexture(context, R.drawable.button_point_race_50points_foc_400x256_png);
        button_point_race_50points_unf = TextureUtils.loadTexture(context, R.drawable.button_point_race_50points_unf_400x256_png);
        button_point_race_150points_foc = TextureUtils.loadTexture(context, R.drawable.button_point_race_150points_foc_584x256_png);
        button_point_race_150points_unf = TextureUtils.loadTexture(context, R.drawable.button_point_race_150points_unf_584x256_png);
        button_point_race_500points_foc = TextureUtils.loadTexture(context, R.drawable.button_point_race_500points_foc_584x256_png);
        button_point_race_500points_unf = TextureUtils.loadTexture(context, R.drawable.button_point_race_500points_unf_584x256_png);

        button_time_race_2min_foc = TextureUtils.loadTexture(context, R.drawable.button_time_race_2min_foc_615x256_png);
        button_time_race_2min_unf = TextureUtils.loadTexture(context, R.drawable.button_time_race_2min_unf_615x256_png);
        button_time_race_5min_foc = TextureUtils.loadTexture(context, R.drawable.button_time_race_5min_foc_615x256_png);
        button_time_race_5min_unf = TextureUtils.loadTexture(context, R.drawable.button_time_race_5min_unf_615x256_png);
        button_time_race_10min_foc = TextureUtils.loadTexture(context, R.drawable.button_time_race_10min_foc_700x256_png);
        button_time_race_10min_unf = TextureUtils.loadTexture(context, R.drawable.button_time_race_10min_unf_700x256_png);

        record_picture_gold_place = TextureUtils.loadTexture(context, R.drawable.records_gold_place_256x256_png);
        record_picture_silver_place = TextureUtils.loadTexture(context, R.drawable.records_silver_place_256x256_png);
        record_picture_bronze_place = TextureUtils.loadTexture(context, R.drawable.records_bronze_place_256x256_png);
        record_picture_iron_place = TextureUtils.loadTexture(context, R.drawable.records_iron_place_256x256_png);


        common_font_digit_0 = TextureUtils.loadTexture(context, R.drawable.common_font_digit_0_png);
        common_font_digit_1 = TextureUtils.loadTexture(context, R.drawable.common_font_digit_1_png);
        common_font_digit_2 = TextureUtils.loadTexture(context, R.drawable.common_font_digit_2_png);
        common_font_digit_3 = TextureUtils.loadTexture(context, R.drawable.common_font_digit_3_png);
        common_font_digit_4 = TextureUtils.loadTexture(context, R.drawable.common_font_digit_4_png);
        common_font_digit_5 = TextureUtils.loadTexture(context, R.drawable.common_font_digit_5_png);
        common_font_digit_6 = TextureUtils.loadTexture(context, R.drawable.common_font_digit_6_png);
        common_font_digit_7 = TextureUtils.loadTexture(context, R.drawable.common_font_digit_7_png);
        common_font_digit_8 = TextureUtils.loadTexture(context, R.drawable.common_font_digit_8_png);
        common_font_digit_9 = TextureUtils.loadTexture(context, R.drawable.common_font_digit_9_png);
        common_font_point = TextureUtils.loadTexture(context, R.drawable.common_font_point_png);
        common_font_plus = TextureUtils.loadTexture(context, R.drawable.common_font_plus_png);
        common_font_minus = TextureUtils.loadTexture(context, R.drawable.common_font_minus_png);


        settings_interface_sizes = TextureUtils.loadTexture(context, R.drawable.text_interface_sizes_423x146_png);
        settings_main_menu = TextureUtils.loadTexture(context, R.drawable.text_main_menu_354x148_png);
        settings_button_to_main_menu = TextureUtils.loadTexture(context, R.drawable.text_button_to_main_menu_354x208_png);
        settings_timer_and_pointer = TextureUtils.loadTexture(context, R.drawable.text_timer_and_pointer_354x208_png);
        settings_start_button = TextureUtils.loadTexture(context, R.drawable.text_start_button_size_478x148_png);
        settings_camera_zoom = TextureUtils.loadTexture(context, R.drawable.text_camera_zoom_294x146_png);

        info_reset_settings = TextureUtils.loadTexture(context, R.drawable.info_reset_settings_1329x719_png);
        info_interacting_with_number = TextureUtils.loadTexture(context, R.drawable.info_interacting_with_number_1329x719_png);
        info_contacts = TextureUtils.loadTexture(context, R.drawable.info_contacts_1329x719_png);


        ok_board_warning_pic = TextureUtils.loadTexture(context, R.drawable.warning_pic_1281x150_png);
        ok_board_piece_green1 = TextureUtils.loadTexture(context, R.drawable.info_green_piece1_128x128_png);
        ok_board_piece_green2 = TextureUtils.loadTexture(context, R.drawable.info_green_piece2_128x128_png);
        ok_board_piece_green3 = TextureUtils.loadTexture(context, R.drawable.info_green_piece3_128x128_png);
        ok_board_piece_grey1 = TextureUtils.loadTexture(context, R.drawable.info_grey_piece1_128x128_png);
        ok_board_piece_grey2 = TextureUtils.loadTexture(context, R.drawable.info_grey_piece2_128x128_png);
        ok_board_piece_grey3 = TextureUtils.loadTexture(context, R.drawable.info_grey_piece3_128x128_png);


        gap_cap = TextureUtils.loadTexture(context, R.drawable.gap_cap_250x50_png);
        tr_wall_cap = TextureUtils.loadTexture(context, R.drawable.gap_cap_250x50_tr50_png);
        wall_side = TextureUtils.loadTexture(context, R.drawable.wall_side_250x50_png);
        tr_wall_side = TextureUtils.loadTexture(context, R.drawable.wall_side_250x50_tr50_png);
        board_side = TextureUtils.loadTexture(context, R.drawable.board_side_1850x250_png);
        stab = TextureUtils.loadTexture(context, R.drawable.stab_png);
    }
    public void uploadTexturesFigures(Context context) {
        background_figures = TextureUtils.loadTexture(context, R.drawable.background_figures_2056x2056_png);

        tile_figures_empty = TextureUtils.loadTexture(context, R.drawable.tile_figures_empty_png);

        tile_circle = TextureUtils.loadTexture(context, R.drawable.tile_circle_png);
        tile_square = TextureUtils.loadTexture(context, R.drawable.tile_square_png);
        tile_four_circles = TextureUtils.loadTexture(context, R.drawable.tile_four_circles_png);
        tile_four_crosses = TextureUtils.loadTexture(context, R.drawable.tile_four_crosses_png);
        tile_cross = TextureUtils.loadTexture(context, R.drawable.tile_cross_png);
        tile_four_squares = TextureUtils.loadTexture(context, R.drawable.tile_four_squares_png);

        tile_tr_circle = TextureUtils.loadTexture(context, R.drawable.tile_circle_tr_png);
        tile_tr_square = TextureUtils.loadTexture(context, R.drawable.tile_square_tr_png);
        tile_tr_four_circles = TextureUtils.loadTexture(context, R.drawable.tile_four_circles_tr_png);
        tile_tr_four_crosses = TextureUtils.loadTexture(context, R.drawable.tile_four_crosses_tr_png);
        tile_tr_cross = TextureUtils.loadTexture(context, R.drawable.tile_cross_tr_png);
        tile_tr_four_squares = TextureUtils.loadTexture(context, R.drawable.tile_four_squares_tr_png);


        cube_figures_side_1 = TextureUtils.loadTexture(context, R.drawable.cube_side_cross_png);
        cube_figures_side_2 = TextureUtils.loadTexture(context, R.drawable.cube_side_circle_png);
        cube_figures_side_3 = TextureUtils.loadTexture(context, R.drawable.cube_side_four_squares_png);
        cube_figures_side_4 = TextureUtils.loadTexture(context, R.drawable.cube_side_square_png);
        cube_figures_side_5 = TextureUtils.loadTexture(context, R.drawable.cube_side_four_circles_png);
        cube_figures_side_6 = TextureUtils.loadTexture(context, R.drawable.cube_side_four_crosses_png);


        platform_figures_left_90 = TextureUtils.loadTexture(context, R.drawable.platform_figures_left_90_png);
        platform_figures_right_90 = TextureUtils.loadTexture(context, R.drawable.platform_figures_right_90_png);
        platform_figures_left_180 = TextureUtils.loadTexture(context, R.drawable.platform_figures_left_180_png);
        platform_figures_right_180 = TextureUtils.loadTexture(context, R.drawable.platform_figures_right_180_png);
    }
    public void uploadTexturesDice(Context context) {
        background_dice = TextureUtils.loadTexture(context, R.drawable.background_dice_2056x2056_png);

        //tile_dice_empty = TextureUtils.loadTexture(context, R.drawable.tile_dice_empty_png);

        tile_numb_1 = TextureUtils.loadTexture(context, R.drawable.tile_1_png);
        tile_numb_2x = TextureUtils.loadTexture(context, R.drawable.tile_2x_png);
        tile_numb_2y = TextureUtils.loadTexture(context, R.drawable.tile_2y_png);
        tile_numb_3xy = TextureUtils.loadTexture(context, R.drawable.tile_3xy_png);
        tile_numb_3xy2 = TextureUtils.loadTexture(context, R.drawable.tile_3xy2_png);
        tile_numb_4 = TextureUtils.loadTexture(context, R.drawable.tile_4_png);
        tile_numb_5 = TextureUtils.loadTexture(context, R.drawable.tile_5_png);
        tile_numb_6x = TextureUtils.loadTexture(context, R.drawable.tile_6x_png);
        tile_numb_6y = TextureUtils.loadTexture(context, R.drawable.tile_6y_png);

        tile_tr_numb_1 = TextureUtils.loadTexture(context, R.drawable.tile_1_tr_png);
        tile_tr_numb_2x = TextureUtils.loadTexture(context, R.drawable.tile_2x_tr_png);
        tile_tr_numb_2y = TextureUtils.loadTexture(context, R.drawable.tile_2y_tr_png);
        tile_tr_numb_3xy = TextureUtils.loadTexture(context, R.drawable.tile_3xy_tr_png);
        tile_tr_numb_3xy2 = TextureUtils.loadTexture(context, R.drawable.tile_3xy2_tr_png);
        tile_tr_numb_4 = TextureUtils.loadTexture(context, R.drawable.tile_4_tr_png);
        tile_tr_numb_5 = TextureUtils.loadTexture(context, R.drawable.tile_5_tr_png);
        tile_tr_numb_6x = TextureUtils.loadTexture(context, R.drawable.tile_6x_tr_png);
        tile_tr_numb_6y = TextureUtils.loadTexture(context, R.drawable.tile_6y_tr_png);


        cube_dice_side_1 = TextureUtils.loadTexture(context, R.drawable.cube_1);
        cube_dice_side_2 = TextureUtils.loadTexture(context, R.drawable.cube_2);
        cube_dice_side_3 = TextureUtils.loadTexture(context, R.drawable.cube_3);
        cube_dice_side_4 = TextureUtils.loadTexture(context, R.drawable.cube_4);
        cube_dice_side_5 = TextureUtils.loadTexture(context, R.drawable.cube_5);
        cube_dice_side_6 = TextureUtils.loadTexture(context, R.drawable.cube_6);


        platform_dice_left_90 = TextureUtils.loadTexture(context, R.drawable.platform_dice_left_90_png);
        platform_dice_right_90 = TextureUtils.loadTexture(context, R.drawable.platform_dice_right_90_png);
        platform_dice_left_180 = TextureUtils.loadTexture(context, R.drawable.platform_dice_left_180_png);
        platform_dice_right_180 = TextureUtils.loadTexture(context, R.drawable.platform_dice_right_180_png);
    }
    public void uploadTexturesXYZ(Context context) {
        background_XYZ = TextureUtils.loadTexture(context, R.drawable.background_xyz_2056x2056_png);

        tile_XYZ_empty = TextureUtils.loadTexture(context, R.drawable.tile_xyz_empty_png);

        tile_XYZ_X = TextureUtils.loadTexture(context, R.drawable.tile_xyz_x_png);
        tile_XYZ_Y1 = TextureUtils.loadTexture(context, R.drawable.tile_xyz_y1_png);
        tile_XYZ_Y2 = TextureUtils.loadTexture(context, R.drawable.tile_xyz_y2_png);
        tile_XYZ_Y3 = TextureUtils.loadTexture(context, R.drawable.tile_xyz_y3_png);
        tile_XYZ_Y4 = TextureUtils.loadTexture(context, R.drawable.tile_xyz_y4_png);
        tile_XYZ_Z1 = TextureUtils.loadTexture(context, R.drawable.tile_xyz_z1_png);
        tile_XYZ_Z2 = TextureUtils.loadTexture(context, R.drawable.tile_xyz_z2_png);
        tile_XYZ_Z3 = TextureUtils.loadTexture(context, R.drawable.tile_xyz_z3_png);
        tile_XYZ_Z4 = TextureUtils.loadTexture(context, R.drawable.tile_xyz_z4_png);

        tile_tr_XYZ_X = TextureUtils.loadTexture(context, R.drawable.tile_xyz_x_tr_png);
        tile_tr_XYZ_Y1 = TextureUtils.loadTexture(context, R.drawable.tile_xyz_y1_tr_png);
        tile_tr_XYZ_Y2 = TextureUtils.loadTexture(context, R.drawable.tile_xyz_y2_tr_png);
        tile_tr_XYZ_Y3 = TextureUtils.loadTexture(context, R.drawable.tile_xyz_y3_tr_png);
        tile_tr_XYZ_Y4 = TextureUtils.loadTexture(context, R.drawable.tile_xyz_y4_tr_png);
        tile_tr_XYZ_Z1 = TextureUtils.loadTexture(context, R.drawable.tile_xyz_z1_tr_png);
        tile_tr_XYZ_Z2 = TextureUtils.loadTexture(context, R.drawable.tile_xyz_z2_tr_png);
        tile_tr_XYZ_Z3 = TextureUtils.loadTexture(context, R.drawable.tile_xyz_z3_tr_png);
        tile_tr_XYZ_Z4 = TextureUtils.loadTexture(context, R.drawable.tile_xyz_z4_tr_png);


        cube_XYZ_X = TextureUtils.loadTexture(context, R.drawable.cube_xyz_x_png);
        cube_XYZ_Y = TextureUtils.loadTexture(context, R.drawable.cube_xyz_y_png);
        cube_XYZ_Z = TextureUtils.loadTexture(context, R.drawable.cube_xyz_z1_png);
        cube_XYZ_Z2 = TextureUtils.loadTexture(context, R.drawable.cube_xyz_z2_png);


        platform_xyz_left_90 = TextureUtils.loadTexture(context, R.drawable.platform_xyz_left_90_png);
        platform_xyz_right_90 = TextureUtils.loadTexture(context, R.drawable.platform_xyz_right_90_png);
        platform_xyz_left_180 = TextureUtils.loadTexture(context, R.drawable.platform_xyz_left_180_png);
        platform_xyz_right_180 = TextureUtils.loadTexture(context, R.drawable.platform_xyz_right_180_png);
    }


    public void deleteTexturesFigures() {
        deleteTexture(background_figures);

        deleteTexture(tile_figures_empty);

        deleteTexture(tile_circle);
        deleteTexture(tile_square);
        deleteTexture(tile_four_circles);
        deleteTexture(tile_four_crosses);
        deleteTexture(tile_cross);
        deleteTexture(tile_four_squares);

        deleteTexture(tile_tr_circle);
        deleteTexture(tile_tr_square);
        deleteTexture(tile_tr_four_circles);
        deleteTexture(tile_tr_four_crosses);
        deleteTexture(tile_tr_cross);
        deleteTexture(tile_tr_four_squares);

        deleteTexture(cube_figures_side_1);
        deleteTexture(cube_figures_side_2);
        deleteTexture(cube_figures_side_3);
        deleteTexture(cube_figures_side_4);
        deleteTexture(cube_figures_side_5);
        deleteTexture(cube_figures_side_6);

        deleteTexture(platform_figures_left_90);
        deleteTexture(platform_figures_right_90);
        deleteTexture(platform_figures_left_180);
        deleteTexture(platform_figures_right_180);
    }
    public void deleteTexturesDice() {
        deleteTexture(background_dice);

        //deleteTexture(tile_dice_empty);

        deleteTexture(tile_numb_1);
        deleteTexture(tile_numb_2x);
        deleteTexture(tile_numb_2y);
        deleteTexture(tile_numb_3xy);
        deleteTexture(tile_numb_3xy2);
        deleteTexture(tile_numb_4);
        deleteTexture(tile_numb_5);
        deleteTexture(tile_numb_6x);
        deleteTexture(tile_numb_6y);

        deleteTexture(tile_tr_numb_1);
        deleteTexture(tile_tr_numb_2x);
        deleteTexture(tile_tr_numb_2y);
        deleteTexture(tile_tr_numb_3xy);
        deleteTexture(tile_tr_numb_3xy2);
        deleteTexture(tile_tr_numb_4);
        deleteTexture(tile_tr_numb_5);
        deleteTexture(tile_tr_numb_6x);
        deleteTexture(tile_tr_numb_6y);

        deleteTexture(cube_dice_side_1);
        deleteTexture(cube_dice_side_2);
        deleteTexture(cube_dice_side_3);
        deleteTexture(cube_dice_side_4);
        deleteTexture(cube_dice_side_5);
        deleteTexture(cube_dice_side_6);

        deleteTexture(platform_dice_left_90);
        deleteTexture(platform_dice_right_90);
        deleteTexture(platform_dice_left_180);
        deleteTexture(platform_dice_right_180);
    }
    public void deleteTexturesXYZ() {
        deleteTexture(background_XYZ);

        deleteTexture(tile_XYZ_empty);

        deleteTexture(tile_XYZ_X);
        deleteTexture(tile_XYZ_Y1);
        deleteTexture(tile_XYZ_Y2);
        deleteTexture(tile_XYZ_Y3);
        deleteTexture(tile_XYZ_Y4);
        deleteTexture(tile_XYZ_Z1);
        deleteTexture(tile_XYZ_Z2);
        deleteTexture(tile_XYZ_Z3);
        deleteTexture(tile_XYZ_Z4);

        deleteTexture(tile_tr_XYZ_X);
        deleteTexture(tile_tr_XYZ_Y1);
        deleteTexture(tile_tr_XYZ_Y2);
        deleteTexture(tile_tr_XYZ_Y3);
        deleteTexture(tile_tr_XYZ_Y4);
        deleteTexture(tile_tr_XYZ_Z1);
        deleteTexture(tile_tr_XYZ_Z2);
        deleteTexture(tile_tr_XYZ_Z3);
        deleteTexture(tile_tr_XYZ_Z4);

        deleteTexture(cube_dice_side_1);
        deleteTexture(cube_dice_side_2);
        deleteTexture(cube_dice_side_3);
        deleteTexture(cube_dice_side_4);
        deleteTexture(cube_dice_side_5);
        deleteTexture(cube_dice_side_6);

        deleteTexture(platform_xyz_left_90);
        deleteTexture(platform_xyz_right_90);
        deleteTexture(platform_xyz_left_180);
        deleteTexture(platform_xyz_right_180);
    }



    private void deleteTexture(int textureId) {
        int[] arr = new int[1];
        arr[0] = textureId;
        glDeleteTextures(1, arr, 0);
    }


    public void changeTextures(GameBoard.GameInfo oldGameInfo, GameBoard.GameInfo newGameInfo) {
        if(oldGameInfo != null) {
            switch (oldGameInfo.gameType) {
                case FIGURES_3:
                case FIGURES_6:
                    deleteTexturesFigures();
                    break;
                case DICE:
                    deleteTexturesDice();
                    break;
                case XYZ:
                    deleteTexturesXYZ();
                    break;
            }
        }
        switch (newGameInfo.gameType) {
            case FIGURES_3:
            case FIGURES_6:
                uploadTexturesFigures(context);
                break;
            case DICE:
                uploadTexturesDice(context);
                break;
            case XYZ:
                uploadTexturesXYZ(context);
                break;
        }
    }


}
