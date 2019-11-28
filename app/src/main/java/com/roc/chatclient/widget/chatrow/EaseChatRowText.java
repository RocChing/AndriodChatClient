package com.roc.chatclient.widget.chatrow;

import android.content.Context;
import android.text.Spannable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.roc.chatclient.R;
import com.roc.chatclient.entity.Msg;
import com.roc.chatclient.util.EaseSmileUtils;

public class EaseChatRowText extends EaseChatRow {

    private TextView contentView;

    public EaseChatRowText(Context context, Msg message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
        inflater.inflate(message.getSender() == currentUserId ?
                R.layout.ease_row_sent_message : R.layout.ease_row_received_message, this);
    }

    @Override
    protected void onFindViewById() {
        contentView = (TextView) findViewById(R.id.tv_chatcontent);
    }

    @Override
    public void onSetUpView() {
//        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        Spannable span = EaseSmileUtils.getSmiledText(context, message.getContent());
        // 设置内容
        contentView.setText(span, BufferType.SPANNABLE);

        handleTextMessage();
    }

    protected void handleTextMessage() {
        if (isSendMsg()) {
            progressBar.setVisibility(GONE);
            statusView.setVisibility(GONE);
        }
//        if (message.direct() == EMMessage.Direct.SEND) {
//            setMessageSendCallback();
//            switch (message.status()) {
//                case CREATE:
//                    progressBar.setVisibility(View.GONE);
//                    statusView.setVisibility(View.VISIBLE);
//                    break;
//                case SUCCESS:
//                    progressBar.setVisibility(View.GONE);
//                    statusView.setVisibility(View.GONE);
//                    break;
//                case FAIL:
//                    progressBar.setVisibility(View.GONE);
//                    statusView.setVisibility(View.VISIBLE);
//                    break;
//                case INPROGRESS:
//                    progressBar.setVisibility(View.VISIBLE);
//                    statusView.setVisibility(View.GONE);
//                    break;
//                default:
//                    break;
//            }
//        } else {
//            if (!message.isAcked() && message.getChatType() == ChatType.Chat) {
//                try {
//                    EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
//                } catch (HyphenateException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onBubbleClick() {
        // TODO Auto-generated method stub

    }
}
