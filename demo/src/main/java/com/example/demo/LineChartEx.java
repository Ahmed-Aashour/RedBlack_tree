package com.example.demo;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class LineChartEx extends JFrame {

    public ArrayList<Long> data;
    public ArrayList<Long> avl;
    public LineChartEx(ArrayList<Long> data, ArrayList<Long> avl) {
        this.avl = avl;
        this.data = data;
        initUI();
    }

    private void initUI() {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("AVL vs RedBlack insertion");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDataset() {

        Double rbAvg = this.data.stream().mapToDouble(a -> a).average().orElse(0.0);
        System.out.println(rbAvg);
        XYSeries series = new XYSeries("redBlack (average = " + Math.round(rbAvg) + ")");
        for (int i = 1; i <= this.data.size(); i++)
        {
            if(i == 1)
            {
                continue;
            }
            series.add(i, this.data.get(i-1));
        }
        

        Double avlAvg = this.avl.stream().mapToDouble(a -> a).average().orElse(0.0);
        System.out.println(avlAvg);
        XYSeries series2 = new XYSeries("AVL (average = " + Math.round(avlAvg) + ")");
        for (int i = 1; i <= this.avl.size(); i++)
        {
            if(i == 1)
            {
                continue;
            }
            series2.add(i, this.avl.get(i-1));
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(series2);

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "AVL vs RedBlack",
                "no. of words",
                "time in nano",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        // renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("AVL vs Redblack insertion",
                        new Font("Consolas", java.awt.Font.BOLD, 18)
                )
        );
        try {
            ChartUtils.saveChartAsPNG(new File("line_chart.png"), chart, 1280, 720);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return chart;
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run()
            {
                RedBlack tree = new RedBlack();
                AVL avl = new AVL();
                Application app = new Application(avl, tree);
                app.startApplication();
                LineChartEx ex = new LineChartEx(app.rB,app.times);
                ex.setVisible(true);
            }
        });

            

    }
}