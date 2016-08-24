package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private PopupWindow pW_menu = null;
    private GridView gridview_menu;
    private RelativeLayout rel_menu_bg;
    private String[] Name = { "多人聊天", "加好友", "扫一扫", "面对面快传", "付款"};
    private int[] Images_ic = {R.drawable.conversation_options_multichat,R.drawable.conversation_options_addmember,R.drawable.conversation_options_qr,R.drawable.conversation_facetoface_qr,R.drawable.conversation_options_charge_icon};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowMenuView(view);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //菜单
    public void ShowMenuView(View anchor) {
        View view = null;
        if (pW_menu == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.cell_menu, null);
            // 创建popWindow
            pW_menu = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT);
        } else {
            view = pW_menu.getContentView();
        }
        gridview_menu=(GridView) view.findViewById(R.id.gridview_menu);
        rel_menu_bg=(RelativeLayout) view.findViewById(R.id.rel_menu_bg);
        rel_menu_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pW_menu.dismiss();
            }
        });
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        //
        // 通过for循环将图片id和列表项文字放到Map中，并添加到list集合中
        for (int i = 0; i < Name.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
             map.put("image", Images_ic[i]);
            map.put("name", Name[i]);
            listItems.add(map); // 将map对象添加到List集合中
        } // 通过for循环将图片id和列表项文字放到Map中，并添加到list集合中

        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.cell_menu_item,
                new String[] { "name", "image" },
                new int[] { R.id.menu_item_name, R.id.menu_item_image });
        gridview_menu.setAdapter(adapter);


        // 可以聚集 ，按返回键，先popWindow，不然popWindow和activity会同时消失，估计这既是Android焦点顺序的原理吧。
        pW_menu.setFocusable(true);
        // view.setFocusable(true); // 这个很重要
        // view.setFocusableInTouchMode(true);
        // 重写onKeyListener
        pW_menu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                // TODO Auto-generated method stub
                //pW_menu.dismiss();
            }
        });
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    // pW_menu.dismiss();
                    return true;
                }
                return false;
            }
        });
        pW_menu.setOutsideTouchable(true);
        // 为了按返回键消失和外部点击消失 ，不会影响背景
        // popWindow.setBackgroundDrawable(new BitmapDrawable());
        pW_menu.setBackgroundDrawable(new ColorDrawable(0x00000000));
        // popWindow.setAnimationStyle(R.style.AnimBottom);
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int xoffInPixels = windowManager.getDefaultDisplay().getWidth() / 2
                - pW_menu.getWidth() / 2;
        int xoffInDip = px2dip(this, xoffInPixels);
        // 默认位置(anchor翻译为“靠山”)
        // popWindow.showAsDropDown(anchor);

        // anchor的居中位置
        //pW_login.showAsDropDown(anchor, -xoffInDip, 0);

        // 偏移位置
        // popWindow.showAsDropDown(anchor,0,-50);
        pW_menu.showAtLocation(
                anchor,
                Gravity.TOP | Gravity.RIGHT, 30, 220);
        pW_menu.update();
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
