/*  This file is part of JunViewer.

    JunViewer is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    JunViewer is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with JunViewer.  If not, see <http://www.gnu.org/licenses/>.  */

package com.waataja.junviewer;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This is a Canvas that has a image of Jun. It randomly selects one and displays it.
 * It also represents the program so it contains the frame.
 * It loads images from a set which is listed in imageNames.
 * Clicking reselects the image and resizes everything.
 * @author jason
 *
 */
public class JunViewerCanvas extends Canvas implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * main window
	 */
	private JFrame mainWindow;
	/**
	 * image to represent Jun
	 */
	private BufferedImage bufferedJunImage;

	/**
	 * folder where the images are stored in the jar file.
	 */
	public final String RESOURCE_FOLDER = "/resources/";
	/**
	 * the preferred width for the window, resizable, though.
	 */
	public static int PREFERRED_WIDTH = 1920 / 3;

	/**
	 * file where the jun's are.
	 */
	private File junDirectory;

	/**
	 * an enum to represent a custom jun folder or to get it from the jar file.
	 * 
	 * @author jason
	 *
	 */
	public enum ChooserMode {
		JAR_MODE, FOLDER_MODE
	}

	/**
	 * The mode that the program is currently in
	 */
	private ChooserMode chooserMode;

	/**
	 * The menu bar
	 */
	private JMenuBar menuBar;

	// names of the images, really bad way to do this
	private String[] IMAGE_NAMES = { "00a6044b8e75b79df3e5bc040fe187b7.png.jpg", "017a6d4c6cfa628af5d2fdadf1f77179.png",
			"095ae33018ec3fe164329a95f1148fcc.png.jpg", "095c0f7ef8e22d693360f6136fa47b4d.png",
			"095f8897bea66fcb233a3e40b85e7564.jpg", "0e079799ca1bb9f9c26c497bdc94a8cc.png.jpg",
			"0e11e9d30f6b2bc28f209c1c5e8d9b2e.jpg", "10801060_p0.jpg", "11464679_p0.jpg", "11508640_p0.jpg",
			"11609460_p0.jpg", "11873681_p0.jpg", "11874824_p0.jpg", "12306612_p0.png", "12523031_p0.jpg",
			"12572670_p0.jpg", "12803239_p0.jpg", "12820169_p0.jpg", "14039026_p0.jpg", "14062562_p0.jpg",
			"14088991_p0.jpg", "14096790_p0.jpg", "14113391_p0.jpg", "14257093_p0.jpg", "14280829_p0.jpg",
			"14281670_p0.png", "14285865_p0.jpg", "14335250_p0.png", "14345787_p0.jpg", "14361458_p0.jpg",
			"14383714_p0.jpg", "14426289_p0.jpg", "14549923_p0.jpg", "14604226_p0.jpg", "14659093_p0.jpg",
			"14703734_p0.png", "147c5e6a8833f4e514c9a253a9eba8ad.jpg", "14896733_p0.jpg", "14912680_p0.jpg",
			"14947726_p0.png", "15006739_p0.jpg", "15029638_p0.jpg", "15042482_p0.png", "15086903_p0.jpg",
			"15146497_p0.jpg", "15171696_p0.jpg", "15385134_p0.jpg", "15392401_p0.jpg",
			"153a20ec8047a25176660d28ac73fd41.png.jpg", "15556685_p0.jpg", "15819171_p0.jpg", "15840471_p0.jpg",
			"15847252_p0.jpg", "15847501_p0.jpg", "15893438_p0.jpg", "15975058_p0.jpg", "16120313_p0.jpg",
			"16371531_p0_master1200.jpg", "16407944_p0.jpg", "16543876_p0.png", "16665261_p0.jpg",
			"167681113df40b16334e1acaf752f640.png", "16769985_p0.jpg", "16811002_p0.jpg", "16987704_p0.jpg",
			"17011294_p0.jpg", "171d2ef6c3d8b7f4cfb07ef3f196540c.png.jpg", "17709851_p0.jpg", "17728545_p0.jpg",
			"17950424_p0.jpg", "17b39cba29fb2d92f9320f4a51814cf9.png.jpg", "18106452_p0.png", "18432946_p0.jpg",
			"18535107_p0_master1200.jpg", "18676643_p0.jpg", "18801870_p0.jpg", "18945087_p0_master1200.jpg",
			"18945087_p1_master1200.jpg", "18966179_p0.jpg", "19061076_p0.jpg", "19129846_p1_master1200.jpg",
			"19279845_p0.jpg", "19489222_p0.png", "19528507_p0.png", "19596547_p0_master1200.jpg",
			"19596547_p1_master1200.jpg", "1cf58f51ae7a2b83c7e1ba68d0cca0fd.jpg.png",
			"1d7fda34b4a30be27e75f84cb867e472.gif", "1d7fda34b4a30be27e75f84cb867e472.gif.jpg.gif",
			"1e0ef12bf06729443321ccd39382c9d3.jpg", "20242461_p0.jpg", "21180707_p0.jpg", "21221923_p0.jpg",
			"21405893_p0.gif", "21807307_p0.jpg", "22440631_p0_master1200.jpg", "22440631_p1_master1200.jpg",
			"22440631_p2_master1200.jpg", "22886204_p0.png", "22921571_p0.jpg", "23006229_p0.png", "23022101_p0.jpg",
			"23171887_p0.jpg", "23193305_p0_master1200.jpg", "23268930_p0.jpg", "23273246_p0.png",
			"23350037_p0_master1200.jpg", "23350037_p1_master1200.jpg", "23360921_p0.jpg", "23598643_p0.jpg",
			"23630701_p0.jpg", "23888480_p0_master1200.jpg", "23888480_p1_master1200.jpg", "24181622_p0.jpg",
			"24367873_p0.jpg", "24376754_p0.jpg", "24393401_p0.jpg", "24424895_p0.jpg", "24442316_p0.jpg",
			"24471337_p1_master1200.jpg", "24489979_p0.gif", "24535490_p0.jpg", "24652022_p0.jpg", "25002851_p0.jpg",
			"25047436_p0.jpg", "25062010_p0.png", "25084767_p0.jpg", "25274975_p0.jpg", "25474441_p0.jpg",
			"25499899_p0.jpg", "25552006_p0_master1200.jpg", "25552006_p1_master1200.jpg", "25624069_p1_master1200.jpg",
			"26056905_p0.jpg", "26145185_p0.png", "26156931_p0.gif", "26359149_p0_master1200.jpg",
			"26359149_p1_master1200.jpg", "266d6dcc7d5b0e7dc62fc8b36552d6b9.png.jpg",
			"2678e8bf0721b69e39ee23a2415dc685-1.jpg", "26801822_p0_master1200.jpg", "26827401_p0.jpg",
			"26835146_p0.jpg", "26952891_p0.jpg", "27162021_p0.jpg", "27becba2733c5848492cc927cc48b761.png",
			"28078199_p0_master1200.jpg", "28078199_p1_master1200.jpg", "28078199_p2_master1200.jpg", "28336353_p0.png",
			"28639838_p0.jpg", "287a80696653fbf39fbe7ab429627ca9.png.jpg", "28864686_p0.jpg", "28947836_p0.jpg",
			"29079658_p0_master1200.jpg", "29079658_p1_master1200.jpg", "2941e855186f56f1004620e9c1956f36.jpg",
			"29685105_p0.jpg", "29726996_p0.jpg", "29962254_p0_master1200.jpg", "29962254_p1_master1200.jpg",
			"29964679_p0.jpg", "2a090da75f981fa435720a25d482a0b8.png", "2b9559b81fc314850b1bf7efe6a0aa43.jpg.png",
			"2c23ac7179e2b8d7974d0edcd3c82f67.png", "2c7c83a7f8769ac8b2434612c0c64883.png.jpg", "2NgGapT.png",
			"30193610_p17_master1200.jpg", "30193610_p6_master1200.jpg", "30202176_p0.png", "30238920_p0.png",
			"306eb7a47d071ed9a20cd948b59af76e.jpg.png", "31052339_p0.jpg", "31170369_p0_master1200.jpg",
			"31170369_p2_master1200.jpg", "31497730_p0_master1200.jpg", "31497730_p1_master1200.jpg", "31673882_p0.jpg",
			"320faa444837f2a8c74552b4a14b84d9.jpg", "32648109_p0.jpg", "32787241_p1_master1200.jpg", "33731326_p0.jpg",
			"338f4fb3dc72849034b38685f31fd2de.jpg", "33926236_p1_master1200.jpg", "33926236_p2_master1200.jpg",
			"347bfa92857c3b8fe5cc7a567355b4d4.jpg.png", "35075836_p0.jpg", "35085175_p0.jpg",
			"35edcea8aa463f5ad95f61f0fc3270b3.jpg", "36246397_p0.jpg", "36708ec7fb2ff0f7d3b286e68ed1a9c3.png",
			"36918416_p22_master1200.png", "36e3bccce601da1abcce4a24b336c81a.jpg",
			"37321d85c751a373b1e192d5a136e043.jpg", "38289bd019a3bd70d31be6e6297c0baa.jpg", "39102690_p0.jpg",
			"39924613_p0.jpg", "3ae9cfceb36298fef4e7cee9672f19c0.png.jpg", "3b71cda08e0a9d37729d9d33f702b28a.png",
			"41ec65d275b95aaaa2a9f42d1eac7e9e.jpg", "42548063_p0.png", "4548e741d7fe91acf85e2c8cf75a809c.jpg",
			"47822288_p2_master1200.jpg", "48753928_p0.jpg", "49467285_p0.png", "49738630_p0_master1200.jpg",
			"4a2379e5073b274a9adc5acb87e579da.jpg", "4db71dca07e15b1b49dc223a139e0f88.jpg",
			"51004376_p0_master1200.png", "51004376_p1_master1200.png", "51110195_p0.jpg", "52587473_p0_master1200.jpg",
			"52ff89832a737f82bbf08f04eddaea9d.jpg", "55d54179c4196e5ccd1f77422429db60.jpg",
			"561d309e3f9c0bf34d095ebf7055e940.jpg", "56257634_p0.jpg", "57076ba0da124270b7140f8070898c32.png.jpg",
			"576e80bb111c5b0d4d35c0a6089b296d.png", "57962f3afeceb9c7765ca40b49c2cc1d.jpg",
			"57e9576082d5645ee053b8806b87bb53.png.jpg", "58b38a469ead62aeb9f22627555a022e.png.jpg",
			"5bf502f51ae4ad4ab43cb4aebd8b156f.jpg", "5e5c2886c2ba98838df2b8ecff953cf6.jpg",
			"5fe8a89d199177dc80ae8e151963a614.jpg", "60cce70bb15d14f0ad108c77eccc93bf.jpg",
			"642432ca0fb8483e8d13abd6e1237bb7.png.jpg", "666647dee490a26eb93e815f50f59f10.png",
			"696d39273934a54b2e75d3e384276b2e.png.jpg", "6b2762f47c0810695f459181573c0b63.jpg",
			"6eb118c3b588f7880c497af72a8401b6.png.jpg", "70abbf6c58d4e2a6dca9938a6eac9222.jpg",
			"711137c915b992653e285f626e088bb9.gif", "7142162bac77f8eca12faba5cc2cb28d.png",
			"723ba2793083ee05578c1088ff215856.png", "75afccbb0f18391ad1595609cfa925e6.png.jpg",
			"78e08760f64e49422a6fa4f0e558cd30.jpg", "79f5e29c10926d02dddf17aee6599303-1.jpg",
			"7ae8a63e4d3c5f9994e5e3c44df01392.png.jpg", "7b62c1fe0ffa8f8edac26a572ea3a8e3.png.jpg", "7C41ltS.jpg",
			"7d5c23b303ef880c8c1aef76d59eda0d.jpg", "7d979ab91145f0c1c132ecd1b9550a71.png.jpg",
			"7e1e432be2a3001bf4da6b0cbc57b3be.gif", "8278cb469afb310d2aca3b72f0bd4ba0.png.jpg",
			"82f01acf76be5e520ac6e07030b3c073.jpg.png", "855c174e455bc7ccf41510d790ae44d8.png.jpg",
			"8b01fa03b71907ec51030c403c95718a.png.jpg", "8b0f1fa3a622f2dbe8452a8639a9e024.png.jpg",
			"8c93d87614c8c68d275f7e04a5945743.jpg", "90cc44f48e7ecc66cbb86ccc69e87044.jpg",
			"9237d049ab4aafa7436a529a2cacf6fd.jpg", "9681cd1f6f23b1e9edd1fb7c5b482ee6.jpg",
			"9a00b2647aaa360cba1d429017893e43.png", "9c71134da7fd9002fd037ab335b6da21.jpg",
			"9cc4fbd10634f8177fec2c6ed4899088.png", "9dacab7bde9386d672f8a8c6bdb491d7.jpg",
			"9e19e613babffe38bc04ebd2d46e26c4.jpg", "a5e38add041f809ac4d385756795416f.jpg",
			"a8bfee278328fa0499c526a500a53151.jpg", "a95fbb24f6e3f52c675ddc964921ea25.png.jpg",
			"aa021b20b8d0796c46451f661fd2ba05.jpg", "ac9394aa23a7f36ba43cf82dff7cdc80.jpg",
			"ae42f54b8ef281b54adfae7c5abf0708.png", "AeAyb.gif", "af5fc472dfb2573ace073cc2d138ee29.png.jpg",
			"af6c47d1f98470bd9d50471eb0e4acd2.png.jpg", "avatar1.png", "AXYxqEu.png",
			"b12bd47e80041e702fb0daf9acf5959f.jpg", "b36c7bda785a3f3a0bf4597b1750ab25.jpg",
			"b3ae869419ec921ed1c6f8aa59394a8a.png.jpg", "b8117f8f57fc949cc3f4ea7843c8f4c3.jpg.png",
			"bae3a3b22b2930e817dd667feff6145d.jpg", "bb6d764468d49fdd8133c0e0a4480457.jpg",
			"bbd06461b68b6dc40425fa51d9ac52f6.jpg", "bd89c0ab8642bf6c740d06d0b4f5b5c3.png.jpg",
			"bf603100ef6fa1d36cf182785b61f09e.png.jpg", "BUTTAN.gif", "c53f398133be3f362c23dad60ce1fbbe.png.jpg",
			"c56308c484b8bd8e98439fc82894e64d.png.jpg", "c6e36d0e2aec431ca6099fd6dc97475a.jpg",
			"c8daca2ee83284eb782ea949b6a93bbc.jpg", "c90238910b2e524e6257bbf9b944cb05.png.jpg",
			"c9c77ee2c2a8d6e7d0187a423990cc65.png.jpg", "ca4e8a18d52d12e1cb3fb6f1823208b1.jpg.png",
			"cd6c8fd9fd37c697330c4d3b2b0b1848.jpg", "cec97016a5703261d7b1d0092b766a21.jpg",
			"cfa181b635d5834b58e510eb04dec938.jpg", "d05823c00b7f1cfd01bf9226bd02bdfc.jpg",
			"d1350b1edf12273b8c2ecc8650e0a5b2.png", "d470f46fb71485615f42adf3c38fa8b0.png.jpg",
			"d565eff187eab7977b2cd552ad7a51d6.png", "d6977319beb425c291cf9f342d138c6c.png",
			"d804a7113ed61b7b040359a1d3115523.jpg", "d8c44e8c6b65154a8cbb2177bb369988.jpg",
			"d93b33fd10f9d80abed0d4859d933440.png.jpg", "d944d958f633d807cfddab925658ef65.jpg.png",
			"d9f8011fafde4270b8e2977b11c469af.jpg", "da3094a816c71530ada4aaa8d841a0ec.gif",
			"dad5188ed87bc58b18271aee46876775.png.jpg", "daf9696fd50124972d4fbd3a68e69732.jpg",
			"dcaaef3a68369a4ef1e55f5c5cf156dd.png", "DEJLDCh.jpg", "e1a6c45f68cb9fcc9c38148069869982.png",
			"e2343288b5f95c901c8980f13da5da5f.png", "e261592926afc64cbf7b2fc779a8089f.png.jpg",
			"e41238b27319795a69d5e1852212d034.png.jpg", "e6cc491754fa009fa87415e110b89593.jpg",
			"e6d31ae0aca5f06a629c8b4387749e49.jpg", "e7b83785454d80fcbd60146b4b185e91.png",
			"e9304ed4ae604d771b479ba122247067.png", "f3d0c3e37d1439ecb085ca9e5b57259a.jpg",
			"f4403ed5dd6a3f14df48498efcb10607.jpg", "fa390ec803ee09d2284154dc0d16b176.png.jpg",
			"fdd80fb096ec8d1943f473e108eb6eed.png.jpg", "fe75f84159120d7d5675cdfa777e4af6.png",
			"fee2f60816e222dc750462b38291f023.jpg", "Gd7zAbS.jpg", "gZkyaKC.png.jpg", "Hd8j5QE.png.jpg",
			"hOKdjjj.png.jpg", "hwslUwe.jpg", "HzKyQbv.png.jpg", "jun (1).png", "jun-onsen_1.png",
			"jun-onsen_1edit.png", "jun-onsen_1edit.xcf", "jun42432.png.jpg", "jungif_1.gif", "jungif_2.gif",
			"jun_1.png.jpg", "jun_2.png.jpg", "jun_3.jpg", "jun_4.jpg", "jun_5.jpg", "jun_hypebeast_1.png",
			"jun_hypebeast_2.png", "Jun_Suzuki_new_mugshot.png", "jx24ru7.png", "mtUPwjL.png", "MzoUll1.png.jpg",
			"pgrz9Z8.gif", "qG8WqB5.png.jpg", "QrnGRQU.png.jpg", "Roo5VbG.png", "RVGqf5D.png",
			"sample-19f4794c880e1e4af33869a9724b89b6.png.jpg", "sample-2ebd5c06ed09fd0dd5e7188fc76df16b (1).jpg",
			"sample-317b048defaae470a928bb0e00c24dae.png.jpg", "sample-4ada8ab89c5a5d1a3846251c35b9b9ed.png.jpg",
			"sample-5cbe464468b603f1c672cd176a6208a7.png.jpg", "sample-69daac954f96e2aa423e66d6af03f489.png.jpg",
			"sample-74bd0036df02646630f2700a5c832b5f.jpg", "sample-96fa471d6740fb41ef8e510ed32f982c.jpg",
			"sample-b2f62256c35e628c993b52702af6374d.jpg", "sample-b2f62256c35e628c993b52702af6374d.png.jpg",
			"sample-c90e8d3d205cb7600d2541ac9e9821c6.png(2).jpg", "sample-c90e8d3d205cb7600d2541ac9e9821c6.png.jpg",
			"sample-d0e773b94bda80919832469b3e33391a.jpg", "sample-d7dcb30ceeb7c9e6bf93ff474bc9e4d1.png.jpg",
			"Screenshot_2015-07-28-16-59-54.png", "Screenshot_2015-07-28-17-00-52.png",
			"Screenshot_2015-07-28-17-01-58.png", "Screenshot_2015-07-28-17-04-32.png",
			"Screenshot_2015-07-28-17-05-43.png", "SFFD4y3.png.jpg",
			"suzuki_jun_render_by_ruby_by_spinelnightmare-d4gaecm.png", "tsVnuNM.png.jpg",
			"tumblr_nwny1x6v3k1uz4mxfo6_r1_500.png", "u1rNvqf.jpg", "u1rNvqf.png.jpg", "uptv0001379.jpg", "vZydJFT.png",
			"WE3B3ZDl.jpg", "wHAlr5s.png", "ynLScq0.png.jpg", "YuionTheJunbutt.jpg", "YuionTheJunbutt2.jpg",
			"YuionTheJunbutt3.jpg", "zf1u9oT.jpg" };

	/**
	 * Constructor for JunViewerCanvas
	 */
	public JunViewerCanvas() {
		bufferedJunImage = null;
		chooserMode = ChooserMode.JAR_MODE;
		createFrame();
		createMenuBar();
		loadJun();
		addMouseListener(this);
	}

	/**
	 * Loads a random Jun into the program. Exits the whole program if it fails.
	 * Tries images until none are left.
	 */
	public void loadJun() {
		// get an arraylist with the list of all the files.
		ArrayList<URL> junFiles;
		switch (chooserMode) {
		case JAR_MODE:
			junFiles = getImagesJarMode();
			break;
		case FOLDER_MODE:
			junFiles = getImagesFolderMode();
			break;
		default:
			junFiles = new ArrayList<URL>();
			break;
		}
		boolean foundFile = false;
		// loop until it's either found the file, or there's no more files left
		// to find
		while (!foundFile && junFiles.size() > 0) {
			int randIndex = (int) (Math.random() * junFiles.size());
			URL junFile = junFiles.get(randIndex);
			// the path is the resource folder with the name of the path
			// appended.
			try {
				// read image.
				bufferedJunImage = ImageIO.read(junFile);
				// if it failed to load the image, remove the image
				if (bufferedJunImage == null) {
					junFiles.remove(randIndex);
				} else {
					// found the image
					setToPreferredWidth();
					// repaints the canvas, just in case.
					repaint();
					foundFile = true;
				}
			} catch (IOException e) {
				// if there was an error reading the file, remove it.
				junFiles.remove(randIndex);
			}
		}
		// If there are no images to be read, display an error and exit the
		// program.
		if (junFiles.size() == 0) {
			String message = "Wasn't able to load an image file.";
			System.err.println(message);
			JOptionPane.showMessageDialog(mainWindow, message, mainWindow.getTitle(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/**
	 * gets the images contained in the jar file.
	 * @return arraylist of the images in the jar.
	 */
	private ArrayList<URL> getImagesJarMode() {
		ArrayList<URL> jarImages = new ArrayList<URL>(IMAGE_NAMES.length);
		for (int i = 0; i < IMAGE_NAMES.length; i++) {
			String junPath = RESOURCE_FOLDER + IMAGE_NAMES[i];
			URL junURL = getClass().getResource(junPath);
			jarImages.add(junURL);
		}
		return jarImages;
	}

	/**
	 * Gets images from the selected folder, not recursive. Shows an error dialog if there folder is null
	 * @return all available images from the folder
	 */
	private ArrayList<URL> getImagesFolderMode() {
		ArrayList<URL> folderImages = new ArrayList<URL>();
		if (junDirectory != null) {
			if (junDirectory.isDirectory()) {
				File[] files = junDirectory.listFiles();
				for (File junFile : files) {
					try {
						folderImages.add(junFile.toURI().toURL());
					} catch (MalformedURLException e) {
						// don't do anything
					}
				}
			}
		} else {
			String message = "No image folder.";
			System.err.println(message);
			JOptionPane.showMessageDialog(mainWindow, message, mainWindow.getTitle(), JOptionPane.ERROR_MESSAGE);
		}
		return folderImages;
	}

	/**
	 * overrides the paint method, draws the image on the canvas. Draws it to
	 * fit the frame.
	 */
	public void paint(Graphics g) {
		// draw the image at (0,0) with the same height
		g.drawImage(bufferedJunImage, 0, 0, getWidth(), getHeight(), this);
	}

	/**
	 * Sets the menus, this would probably be better done with a real editor and xml or something.
	 */
	private void createMenuBar() {
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu setImageSubmenu = new JMenu("Set Image");
		JMenuItem setJarModeItem = new JMenuItem("From Jar");
		setJarModeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooserMode = ChooserMode.JAR_MODE;
				loadJun();
			}
		});
		setImageSubmenu.add(setJarModeItem);
		JMenuItem setFolderModeItem = new JMenuItem("From Folder");
		setFolderModeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				boolean getDirectory = true;
				while (getDirectory) {
					int returnValue = chooser.showOpenDialog(mainWindow);
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						junDirectory = chooser.getSelectedFile();
						getDirectory = false;
						chooserMode = ChooserMode.FOLDER_MODE;
					} else if (returnValue == JFileChooser.CANCEL_OPTION) {
						getDirectory = false;
					} else {
						JOptionPane.showMessageDialog(mainWindow, "Please select a directory.");
					}
				}
			}
		});
		setImageSubmenu.add(setFolderModeItem);
		fileMenu.add(setImageSubmenu);
		JMenuItem nextItem = new JMenuItem("Next Image");
		nextItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				loadJun();
			}
		});
		fileMenu.add(nextItem);
		fileMenu.addSeparator();
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		JMenu helpMenu = new JMenu("Help");
		JMenuItem shortcutsItem = new JMenuItem("Shortcuts");
		shortcutsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = "Left Click: Next Image\n"
						+ "Right Click: Resize to Original Image Size\n"
						+ "Middle Click: Set Width to 640 Pixels\n";
				JOptionPane.showMessageDialog(mainWindow, message);
			}
		});
		helpMenu.add(shortcutsItem);
		menuBar.add(helpMenu);

		mainWindow.setJMenuBar(menuBar);
	}

	/**
	 * creates the main window, not the menus, though.
	 */
	private void createFrame() {
		mainWindow = new JFrame();
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setResizable(true);
		mainWindow.setVisible(true);
		mainWindow.add(BorderLayout.CENTER, this);
	}

	/**
	 * Set the canvas width and height and pack the canvas
	 * 
	 * @param width
	 *            width of the new size
	 * @param height
	 *            height of the new size
	 */
	public void resizeComponents(int width, int height) {
		this.setSize(width, height);
		if (mainWindow != null) {
			mainWindow.pack();
		}
	}

	/**
	 * resizes everything to be the preferred native resolution of the image.
	 */
	public void setToImageSize() {
		if (bufferedJunImage != null) {
			int width = bufferedJunImage.getWidth();
			int height = bufferedJunImage.getHeight();
			resizeComponents(width, height);
		}
	}

	/**
	 * sets the width of the canvas to the preferred width.
	 */
	public void setToPreferredWidth() {
		int width = PREFERRED_WIDTH;
		// height is proportional to the image dimensions and with
		// the preferred width.
		int height = bufferedJunImage.getHeight() * PREFERRED_WIDTH / bufferedJunImage.getWidth();
		resizeComponents(width, height);
	}

	public static void main(String[] args) {
		JunViewerCanvas canvas = new JunViewerCanvas();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getButton() == MouseEvent.BUTTON1) {
			loadJun();
		}
		if (arg0.getButton() == MouseEvent.BUTTON3) {
			setToImageSize();
		}
		if (arg0.getButton() == MouseEvent.BUTTON2) {
			setToPreferredWidth();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
