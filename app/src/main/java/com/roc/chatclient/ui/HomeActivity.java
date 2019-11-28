package com.roc.chatclient.ui;

import com.roc.chatclient.R;
import com.roc.chatclient.adapter.MainTabAdpter;
import com.roc.chatclient.db.InviteMessageDao;
import com.roc.chatclient.model.ChatHelper;
import com.roc.chatclient.model.CmdInfo;
import com.roc.chatclient.model.Constant;
import com.roc.chatclient.model.ReceiveMsgInfo;
import com.roc.chatclient.receiver.IMsgCallback;
import com.roc.chatclient.ui.fragment.ContactListFragment;
import com.roc.chatclient.ui.fragment.ConversationListFragment;
import com.roc.chatclient.ui.fragment.Fragment_Dicover;
import com.roc.chatclient.ui.fragment.Fragment_Profile;
import com.roc.chatclient.util.CommonUtils;
import com.roc.chatclient.util.MFGT;
import com.roc.chatclient.widget.DMTabHost;
import com.roc.chatclient.widget.MFViewPager;

import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends BaseActivity implements DMTabHost.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    private String Tag = "HomeActivity";
    private MainTabAdpter adapter;
    private int keyBackClickCount = 0;
    private int PageIndex = 0;
    private DMTabHost mHost;
    private MFViewPager viewpager;
    private TextView txt_title;
    private ImageView img_right;
    //private TitlePopup titlePopup;
//    private ConversationListFragment conversationListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mHost = findViewById(R.id.tab_host);
        viewpager = findViewById(R.id.viewpager);
        txt_title = findViewById(R.id.txt_title);
        img_right = findViewById(R.id.img_right);

        initMainTab();
    }

    private void initMainTab() {
        mHost.setChecked(0);
        mHost.setOnCheckedChangeListener(this);
        mHost.setHasNew(2, true);

        adapter = new MainTabAdpter(getSupportFragmentManager());
        adapter.clear();
//        viewpager.setScrollble(false);

        adapter.addFragment(new ConversationListFragment(), getString(R.string.app_name));
        adapter.addFragment(new ContactListFragment(), getString(R.string.contacts));
        adapter.addFragment(new Fragment_Dicover(), getString(R.string.discover));
        adapter.addFragment(new Fragment_Profile(), getString(R.string.me));
        adapter.notifyDataSetChanged();

        viewpager.setOffscreenPageLimit(4);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(this);

        img_right.setVisibility(View.VISIBLE);
        img_right.setImageResource(R.drawable.icon_add);

        ChatHelper.getInstance().setCheckMsgCallback(new IMsgCallback() {
            @Override
            public void HandleMsg(CmdInfo info, String status) {
                //String status = info.Data.toString();
                ConversationListFragment conversationListFragment = (ConversationListFragment) adapter.getItem(0);
                if (status.equalsIgnoreCase(Constant.SUCCESS)) {
                    conversationListFragment.setErrorText(View.GONE);
                } else {
                    conversationListFragment.setErrorText(View.VISIBLE);
                }
            }

            @Override
            public void HandleError(CmdInfo info, String msg) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Tag, "onRestart the PageIndex is:" + PageIndex);

       // refreshMsg();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag, "onResume the PageIndex is:" + PageIndex);
        //refreshMsg();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (keyBackClickCount++) {
                case 0:
                    CommonUtils.showLongToast(getString(R.string.key_back_msg));
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            keyBackClickCount = 0;
                        }
                    }, 3000);
                    break;
                case 1:
                    MFGT.finishFormBottom(this);
                    break;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCheckedChange(int checkedPosition, boolean byUser) {
        setTabMsg(checkedPosition);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mHost.setChecked(position);
        setTabMsg(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setTabMsg(int checkedPosition) {
        PageIndex = checkedPosition;
        viewpager.setCurrentItem(checkedPosition);
        switch (checkedPosition) {
            case 0:
                img_right.setVisibility(View.VISIBLE);
                img_right.setImageResource(R.drawable.icon_add);
                txt_title.setText(getString(R.string.app_name));
                break;
            case 1:
                img_right.setVisibility(View.VISIBLE);
                img_right.setImageResource(R.drawable.icon_titleaddfriend);
                txt_title.setText(getString(R.string.contacts));
                break;
            case 2:
                img_right.setVisibility(View.GONE);
                txt_title.setText(getString(R.string.discover));
                break;
            case 3:
                img_right.setVisibility(View.GONE);
                txt_title.setText(getString(R.string.me));
                break;
        }
    }

    private void refreshMsg() {
        ChatHelper.getInstance().resetOnlyData();
        ConversationListFragment fragment = (ConversationListFragment) adapter.getItem(0);
        fragment.refresh();
    }

    /**
     * 获取未读申请与通知消息
     *
     * @return
     */
    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal = 0;
        InviteMessageDao inviteMessgeDao = new InviteMessageDao(this);
        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
        return unreadAddressCountTotal;
    }
}
