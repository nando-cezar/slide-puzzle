package view.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import interfaces.PuzzleBoardListener;

public class Stopwatch extends JPanel{

	private static final long serialVersionUID = 1L;
	private PuzzleBoardListener puzzleBoardListener;
	private JLabel labelTime;
	private JButton btnStart;
	private JButton btnPause;
	private JButton btnStop;
	private Timer timer;
	private boolean isRunning;
	private boolean isVisible;
	private long initialTime;
	private long milliSeconds;
	private ActionListener taskPerformer = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			long currentTime = new Date().getTime();
			milliSeconds = currentTime - initialTime;
			labelTime.setText(getStringTimer());
		}
	};

	private ActionListener onPause = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			labelTime.setVisible(isVisible = !isVisible);
		}
	};

	public Stopwatch(PuzzleBoardListener puzzleBoardListener) {
		this.puzzleBoardListener = puzzleBoardListener;
		this.isRunning = false;
		this.isVisible = true;
		this.milliSeconds = 0;
		this.timer = new Timer(10, null);
		this.labelTime = new JLabel(getStringTimer());
		this.initialize();
	}
	
	public Stopwatch(PuzzleBoardListener puzzleBoardListener, long milliSeconds) {
		this.puzzleBoardListener = puzzleBoardListener;
		this.isRunning = false;
		this.isVisible = true;
		this.milliSeconds = milliSeconds;
		this.timer = new Timer(10, onPause);
		this.labelTime = new JLabel(getStringTimer());
		this.initialize();
	}
	
	private String getStringTimer() {
		return String.format("%02d:%02d:%02d:%02d", getHours(), getMinutes(), getSeconds(), getHundredthSeconds());
	}

	public long getMilliSeconds() {
		return milliSeconds;
	}

	private long getHundredthSeconds() {
		return milliSeconds / 10 % 100;
	}

	public long getSeconds() {
		return milliSeconds / 1000 % 60;
	}

	private long getMinutes() {
		return milliSeconds / 1000 / 60 % 60;
	}

	private long getHours() {
		return milliSeconds / 1000 / 60 / 60 % 24;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void initialize() {	
		MouseAdapter hoverEffect = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				e.getComponent().setBackground(new Color(249, 13, 72));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				e.getComponent().setBackground(new Color(0, 0, 128));
			}
		};
		
		this.setLayout(null);
		this.setBounds(10, 0, 630, 70);
		this.setBorder(new LineBorder(new Color(0, 0, 128)));

		labelTime.setFont(new Font("Tahoma", Font.PLAIN, 30));
		labelTime.setBounds(10, 10, 200, 50);

		btnStart = new CustomButton("", "img\\icons\\icon-play.png", new Color(255, 255, 255), new Color(0, 0, 128),
				390, 10, 70, 50);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		btnStart.addMouseListener(hoverEffect);
		
		btnPause = new CustomButton("", "img\\icons\\icon-pause.png", new Color(255, 255, 255), new Color(0, 0, 128),
				470, 10, 70, 50);
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				puzzleBoardListener.keep();
			}
		});
		btnPause.addMouseListener(hoverEffect);
		
		btnStop = new CustomButton("", "img\\icons\\icon-stop.png", new Color(255, 255, 255), new Color(0, 0, 128), 550,
				10, 70, 50);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		btnStop.addMouseListener(hoverEffect);

		btnStop.setEnabled(false);
		btnStart.setEnabled(true);
		btnPause.setEnabled(false);

		Arrays.asList(labelTime, btnStart, btnPause, btnStop).forEach(this::add);
		
	}

	private void start() {
		switchTimer();
		long currentTime = new Date().getTime();
		initialTime = currentTime - milliSeconds;
		btnStart.setEnabled(false);
		btnPause.setEnabled(true);
		btnStop.setEnabled(true);
	}

	public void pause() {
		switchTimer();
		btnPause.setEnabled(false);
		btnStart.setEnabled(true);
		btnStop.setEnabled(true);
	}

	public void stop() {
		if (timer.isRunning()) {
			timer.stop();
			Arrays.asList(onPause, taskPerformer).forEach(timer::removeActionListener);
			milliSeconds = 0;
			isRunning = false;
			btnStop.setEnabled(false);
			btnStart.setEnabled(true);
			btnPause.setEnabled(false);
			labelTime.setText("00:00:00:00");
			labelTime.setVisible(true);
		}
	}

	public void switchTimer() {
		isRunning = !isRunning;
		timer.stop();
		if (isRunning) {
			timer.addActionListener(taskPerformer);
			timer.removeActionListener(onPause);
			timer.setDelay(10);
			labelTime.setVisible(true);
		} else {
			timer.removeActionListener(taskPerformer);
			timer.addActionListener(onPause);
			timer.setDelay(500);
		}
		timer.start();
	}
}
