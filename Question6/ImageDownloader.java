package Question6;

import java.awt.*;
import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//  representing the Image Downloader application
public class ImageDownloader extends JFrame {

    private JTextField urlTextField;
    private JButton downloadButton;
    private JButton pauseButton;
    private JButton resumeButton;
    private JButton cancelButton;
    private JTextArea statusTextArea;
    private List<DownloadTaskPanel> taskPanels;
    private ExecutorService executorService;
    private ImageDownloadTask currentTask;


    
    // Constructor for the ImageDownloader
    public ImageDownloader() {
        setTitle("Image Downloader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        executorService = Executors.newFixedThreadPool(5);// Creating a fixed thread pool
        taskPanels = new CopyOnWriteArrayList<>();
    }

        // Initialize GUI components
    private void initComponents() {
        urlTextField = new JTextField(30);
        downloadButton = new JButton("Download");
        pauseButton = new JButton("Pause");
        resumeButton = new JButton("Resume");
        cancelButton = new JButton("Cancel");
        statusTextArea = new JTextArea(15, 30);
        statusTextArea.setEditable(false);

         // ActionListener for the Download button
    downloadButton.addActionListener(e -> {
        String url = urlTextField.getText().trim();
        if (!url.isEmpty() && (currentTask == null || (currentTask.isDone()))) {
            downloadImage(url);
        } else{
            JOptionPane.showMessageDialog(this, "A download is already in progress.");
        }
    });

      // ActionListener for the Pause button
        pauseButton.addActionListener(e -> {
            if (currentTask != null) {
                currentTask.pause();
                statusTextArea.append("Download paused.\n");
            }
        });

          // ActionListener for the Resume button
        resumeButton.addActionListener(e -> {
            if (currentTask != null) {
                currentTask.resume();
                statusTextArea.append("Download resumed.\n");
            }
        });

         // ActionListener for the Cancel button
        cancelButton.addActionListener(e -> {
            if (currentTask != null) {
                currentTask.cancel();
                statusTextArea.append("Download canceled.\n");
                currentTask = null;  
                removeTaskPanel();

            }
        });

        setLayout(new BorderLayout());

        
        // Create and add components to the top panel
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Enter URL:"));
        topPanel.add(urlTextField);
        topPanel.add(downloadButton);
        topPanel.add(pauseButton);
        topPanel.add(resumeButton);
        topPanel.add(cancelButton);
        add(topPanel, BorderLayout.NORTH);

        // Create and add a scrollable text area to the center of the frame
        JScrollPane scrollPane = new JScrollPane(statusTextArea);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    // Method to initiate image download
    private void downloadImage(String url) {
        currentTask = new ImageDownloadTask(url, statusTextArea, taskPanels);
        executorService.submit(currentTask);

        // Creating  a panel to display the progress of this download task
        DownloadTaskPanel taskPanel = new DownloadTaskPanel(url);
        taskPanels.add(taskPanel);
        add(taskPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
    private void removeTaskPanel() {
        if (!taskPanels.isEmpty()) {
            DownloadTaskPanel removedPanel = taskPanels.remove(taskPanels.size() - 1);
            remove(removedPanel);
            revalidate();
            repaint();
        }
    }


    //  method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageDownloader());
    }
}

//  representing the Image Download Task
class ImageDownloadTask implements Runnable {

    private final String imageUrl;
    private final JTextArea statusTextArea;
    private final List<DownloadTaskPanel> taskPanels;
    private final Lock lock = new ReentrantLock();

    private volatile boolean canceled;
    private volatile boolean paused;
    private volatile boolean completed; 

   

    public ImageDownloadTask(String imageUrl, JTextArea statusTextArea, List<DownloadTaskPanel> taskPanels) {
        this.imageUrl = imageUrl;
        this.statusTextArea = statusTextArea;
        this.taskPanels = taskPanels;
    }

    
    // Run method that simulates downloading an image in chunks
    @Override
    public void run() {
        try {
            URL url = new URL(imageUrl);

            if (!canceled) {
                completed = false;
                int totalChunks = 10;
                for (int i = 1; i <= totalChunks; i++) {
                    int progress = i * 10;
                    checkPaused();

                    SwingUtilities.invokeLater(() -> {
                        appendStatus("Downloading: " + imageUrl + " - " + progress + "%");
                        updateProgressBar(progress);
                    });

                    Thread.sleep(500);  // Simulate chunk download time
                    if (canceled) {
                        SwingUtilities.invokeLater(() -> appendStatus("Download canceled: " + imageUrl));
                        return; // Exit the download task if canceled
                    }
                }
                SwingUtilities.invokeLater(() -> appendStatus("Downloaded: " + imageUrl));
                completed = true;
            } 
        } catch (MalformedURLException e) {
            SwingUtilities.invokeLater(() -> appendStatus("Invalid URL: " + imageUrl));
        } catch (InterruptedException e) {
            SwingUtilities.invokeLater(() -> appendStatus("Download interrupted: " + imageUrl));
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> appendStatus("Error downloading: " + imageUrl));
        } finally {
            updateProgressBar(100);
        }
    }

    // Method to check if the download is paused
    private void checkPaused() throws InterruptedException {
        lock.lock();
        try {
            while (paused) {
                lock.unlock();
                Thread.sleep(100); // Sleep to avoid busy-wait
                lock.lock();
            }
        } finally {
            lock.unlock();
        }
    }
    
     // Method to update progress bar in all task panels
    private void updateProgressBar(int progress) {
        SwingUtilities.invokeLater(() -> {
            for (DownloadTaskPanel taskPanel : taskPanels) {
                taskPanel.setProgress(progress);
            }
        });
    }

        // Method to append status messages to the text area
    private void appendStatus(String message) {
        lock.lock();
        try {
            statusTextArea.append(message + "\n");
        } finally {
            lock.unlock();
        }
    }
    // Method to check if the download is completed
    public boolean isDone() {
        return completed ;
    }
   
      // Method to cancel the download
    public void cancel() {
        canceled = true;
       
    }
    
    // Method to pause the download
    public void pause() {
        paused = true;
    }

     // Method to resume the download
    public void resume() {
        paused = false;
    }
}

//  representing a panel to display the progress of a download task
class DownloadTaskPanel extends JPanel {

    private final JProgressBar progressBar;
    private final JLabel statusLabel;

     // Constructor for DownloadTaskPanel
    public DownloadTaskPanel(String url) {
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createTitledBorder(url));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        statusLabel = new JLabel("Downloading...");

        add(progressBar);
        add(statusLabel);
    }

     // Method to set the progress of the progress bar
    public void setProgress(int progress) {
        progressBar.setValue(progress);
        progressBar.setString(progress + "%");

        if (progress == 100) {
            statusLabel.setText("Completed");
        }
    }
}
