package com.jiecheng.zhike.mydemo.lesson.Fragments;

/*import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;*/

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.lesson.Activitys.NewsContentActivity;
import com.jiecheng.zhike.mydemo.lesson.models.News;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*import java.util.ArrayList;
import java.util.List;
import java.util.Random;*/
/*import java.util.ArrayList;
import java.util.List;
import java.util.Random;*/

/**
 * Created by 13159 on 2017/8/11.
 */

public class NewsTitleFragment extends Fragment {
    private boolean isTwoPane;
    private LayoutInflater layoutInftater;
    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
        private  List<News> mNewsList;
        public NewsAdapter(List<News> newsList){
            mNewsList=newsList;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_news_item,parent,false);
            final ViewHolder holder=new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    News news=mNewsList.get(holder.getAdapterPosition());
                    if(isTwoPane){
                        NewsContentFragment newsContentFragment= (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(),news.getContent());
                    }else{
                        NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
                    }
                }
            });
            return holder;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            News news=mNewsList.get(position);
            holder.newTitleText.setText(news.getTitle());
        }
        @Override
        public int getItemCount() {
            return mNewsList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView newTitleText;

            public ViewHolder(View view) {
                super(view);
                newTitleText = (TextView) view.findViewById(R.id.news_title);
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lesson_news_title_frag,container,false);
        RecyclerView newsTitteRecycterView = (RecyclerView) view.findViewById(R.id.news_title_recycler_view);
        LinearLayoutManager tayoutManager = new LinearLayoutManager(getActivity());
        newsTitteRecycterView.setLayoutManager(tayoutManager);
        NewsAdapter adapter = new NewsAdapter(getNews());
        newsTitteRecycterView.setAdapter(adapter);
        return view;
    }
    private List<News> getNews(){
        List<News> list = new ArrayList<News>();
        for(int i=0;i<=50;i++){
            News news = new News();
            news.setTitle("中国人民解放军建军90周年"+i);
            news.setContent(getRandLengthcontent("This is new content"+i+""));
            list.add(news);
        }
        return list;
    }
    private String getRandLengthcontent(String content){
        Random random = new Random();
        int length = random.nextInt(20)+1;
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<length;i++){
            stringBuilder.append(content);
        }
        return stringBuilder.toString();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.news_content_layout)!= null){
            isTwoPane = true;//可以找到news_ content_ layout布局时，为双页模式
        }else{
            isTwoPane = false;//找不到news_content_layout布局时，为单页模式
        }

    }
}
