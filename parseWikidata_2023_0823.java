/* 
 * WikidataのN-Triple形式のダンプファイルから， 
 * 条件に合致するトリプルを抽出するプログラムのサンプル
 * 
 * 2023/08/23 Kouji Kozaki
 *
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class parseWikidata_2023_0823 {

	public static void main(String args[]) {

		long startTime = System.currentTimeMillis();

		long i=0;	//何行目を処理しているかを記録する変数
		
		/* 読み込むファイルと出力フォルダの指定 */
		String inputFilename = "D:KG/Wikidata-2023-0812/latest-truthy.nt";
		String outputFolder = "D:KG/Wikidata-2023-0812/output-2023-0823/";
		
		/* 主語の種類を表す定数 */
		final int WD_ENTITY = 0;
		final int WD_PROPERTY = 1;
		final int WD_OTHER = 2;
		
		
		
		try {
			/*読み込むファイルの指定*/
			File inputFile = new File(inputFilename);
			BufferedReader br = new BufferedReader(	new InputStreamReader(new FileInputStream(inputFile),"UTF-8"));


			/*保存するファイルの指定*/
			/*ファイ名，Witer，行数カウント表のintを用意 */
			
			//主語がWikidtaのEntit/Prpoertyでないもの【テスト用】
			File saveOTHER = new File(outputFolder+"others.nt");
			BufferedWriter bwOTHER = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveOTHER), "UTF-8"));
			int numOTHER = 0;
			
			
			
			//日本語ラベル
			File saveJAL = new File(outputFolder+"ja-label.nt");
			BufferedWriter bwJAL = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveJAL), "UTF-8"));
			
			File saveJALp = new File(outputFolder+"ja-label-p.nt");
			BufferedWriter bwJALp = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveJALp), "UTF-8"));
			
			BufferedWriter[] bwJALs = {bwJAL,bwJALp,bwOTHER};
			int[] numJALs = {0,0,0};
			
			//英語ラベル
			File saveENL = new File(outputFolder+"en-label.nt");
			BufferedWriter bwENL = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveENL), "UTF-8"));
			
			File saveENLp = new File(outputFolder+"en-label-p.nt");
			BufferedWriter bwENLp = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveENLp), "UTF-8"));
			
			BufferedWriter[] bwENLs = {bwENL,bwENLp,bwOTHER};
			int[] numENLs = {0,0,0};
			
			
			//日本語別名
			File saveJAAL = new File(outputFolder+"ja-altLabel.nt");
			BufferedWriter bwJAAL = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveJAAL), "UTF-8"));
			
			File saveJAALp = new File(outputFolder+"ja-altLabel-p.nt");
			BufferedWriter bwJAALp = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveJAALp), "UTF-8"));
			
			BufferedWriter[] bwJAALs = {bwJAAL,bwJAALp,bwOTHER};
			int[] numJAALs = {0,0,0};
			
			
			//英語別名
			File saveENAL = new File(outputFolder+"en-altLabel.nt");
			BufferedWriter bwENAL = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveENAL), "UTF-8"));
			
			File saveENALp = new File(outputFolder+"en-altLabel-p.nt");
			BufferedWriter bwENALp = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveENALp), "UTF-8"));
			
			BufferedWriter[] bwENALs = {bwENAL,bwENALp,bwOTHER};
			int[] numENALs = {0,0,0};
			
			
			//日本語概要
			File saveJADES = new File(outputFolder+"ja-description.nt");
			BufferedWriter bwJADES = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveJADES), "UTF-8"));
			
			File saveJADESp = new File(outputFolder+"ja-description-p.nt");
			BufferedWriter bwJADESp = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveJADESp), "UTF-8"));
			
			BufferedWriter[] bwJADESs = {bwJADES,bwJADESp,bwOTHER};
			int[] numJADESs = {0,0,0};
			
			//英語概要
			File saveENDES = new File(outputFolder+"en-description.nt");
			BufferedWriter bwENDES = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveENDES), "UTF-8"));
			
			File saveENDESp = new File(outputFolder+"en-description-p.nt");
			BufferedWriter bwENDESp = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveENDESp), "UTF-8"));
			
			BufferedWriter[] bwENDESs = {bwENDES,bwENDESp,bwOTHER};
			int[] numENDESs = {0,0,0};
			
			
			//日本語その他リテラル
			File saveJAO = new File(outputFolder+"ja-other.nt");
			BufferedWriter bwJAO = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveJAO), "UTF-8"));
			
			File saveJAOp = new File(outputFolder+"ja-other-p.nt");
			BufferedWriter bwJAOp = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveJAOp), "UTF-8"));
			
			BufferedWriter[] bwJAOs = {bwJAO,bwJAOp,bwOTHER};
			int[] numJAOLs = {0,0,0};
			
			//英語その他リテラル
			File saveENO = new File(outputFolder+"en-other.nt");
			BufferedWriter bwENO = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveENO), "UTF-8"));
			
			File saveENOp = new File(outputFolder+"en-other-p.nt");
			BufferedWriter bwENOp = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveENOp), "UTF-8"));
			
			BufferedWriter[] bwENOs = {bwENO,bwENOp,bwOTHER};
			int[] numENOLs = {0,0,0};
			
			
			//P31(分類/instance-of)			
			File saveP31 = new File(outputFolder+"P31.nt");
			BufferedWriter bwP31 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveP31), "UTF-8"));
			
			File saveP31p = new File(outputFolder+"P30-p.nt");
			BufferedWriter bwP31p = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveP31p), "UTF-8"));
			
			BufferedWriter[] bwP31s = {bwP31,bwP31p,bwOTHER};
			int[] numP31s = {0,0,0};
	
			//P279(上位クラス/sub-class-of)			
			File saveP279 = new File(outputFolder+"P279.nt");
			BufferedWriter bwP279 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveP279), "UTF-8"));
			
			File saveP279p = new File(outputFolder+"P279-p.nt");
			BufferedWriter bwP279p = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveP279p), "UTF-8"));
			
			BufferedWriter[] bwP279s = {bwP279,bwP279p,bwOTHER};
			int[] numP279s = {0,0,0};
			
			
			//述語がその他，目的語がWikidataのEntity（/Prpoerty含む）			
			File saveWDO = new File(outputFolder+"wd-other.nt");
			BufferedWriter bwWDO = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveWDO), "UTF-8"));
			
			File saveWDOp = new File(outputFolder+"wd-other-p.nt");
			BufferedWriter bwWDOp = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveWDOp), "UTF-8"));
			
			BufferedWriter[] bwWDOs = {bwWDO,bwWDOp,bwOTHER};
			int[] numWDOs = {0,0,0};
			

			//述語がその他，目的語がその他のURL			
			File saveOUO = new File(outputFolder+"url-other.nt");
			BufferedWriter bwOUO = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveOUO), "UTF-8"));
			
			File saveOUOp = new File(outputFolder+"url-other-p.nt");
			BufferedWriter bwOUOp = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveOUOp), "UTF-8"));
			
			BufferedWriter[] bwOUOs = {bwOUO,bwOUOp,bwOTHER};
			int[] numWDOUs = {0,0,0};
			
			
			//述語がその他，目的語がその他のリテラル			
			File saveOLO = new File(outputFolder+"literal-other.nt");
			BufferedWriter bwOLO = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveOLO), "UTF-8"));
			
			File saveOLOp = new File(outputFolder+"literal-other-p.nt");
			BufferedWriter bwOLOp = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveOLOp), "UTF-8"));
			
			BufferedWriter[] bwOLOs = {bwOLO,bwOLOp,bwOTHER};
			int[] numOLOs = {0,0,0};
			
			
			
			/*読み込みに使う変数*/
			String line="";	//1行ずつ読み込むための変数
			int wd_type = 0; //主語の種類（WikidataのEntit/Prpoerty/その他）
			//long c=0;	//件数のカウント用の変数

			//ファイルを1行ずつ読み込んで処理する
			//参考）Wikidataダンプ全体を処理すると10:45 -> 12:11 約1時間半かかった
			while(br.ready()) {
				line = br.readLine();

				//c++;
				//処理した行数を100000行毎に標準出力へ表示する
				//（1行ごとに表示するとパフォーマンスが落ちるので，一定行数ごとに出力している）
				i++;
				if(i % 100000 == 0) {
					System.out.println(i);
				}
//				//処理テスト用に最初の10000000行で処理を止めるときは下記のコメントを外す
//				if(i == 100000000 ) {
//					break;
//				}

				//デバッグ用
				//System.out.println(line);



			//以下がメインの処理
				
				//wc:P を wdt:に置き換える処理を入れるならここ
				//line.replaceAll("", "");

				//読み込んだ1行をトリプルに分解する
				String[] data = line.split(" ");

				if(data.length>=3) {
					
					//主語の種類の確認
					if(data[0].startsWith("<http://www.wikidata.org/entity/Q")){
						wd_type = WD_ENTITY;
					}
					else if(data[0].startsWith("<http://www.wikidata.org/entity/P")){
						wd_type = WD_PROPERTY;
					}
					else {
						wd_type = WD_OTHER;
					}
					
					//目的語に半角スペースが含まれている際を考慮した処理					
					String objstr = line.replace(data[0]+" "+data[1], "");
					int last = objstr.lastIndexOf(" ");
					data[2] = objstr.substring(0, last).trim();
					
					
				//指定した述語の確認
				//目的語が「リテラル」（日本語or英語）	
					//述語がrdfs:label
					if(data[1].equals("<http://www.w3.org/2000/01/rdf-schema#label>")){
						if(data[2].endsWith("@ja")) {
							bwJALs[wd_type].write(line+"\n");
							numJALs[wd_type]++;
						}
						else if(data[2].endsWith("@en")) {
							bwENLs[wd_type].write(line+"\n");
							numENLs[wd_type]++;
							}
					}
					else if(data[1].equals("<http://schema.org/name>")){
						//<http://schema.org/name>は出力しない
					}
					else if(data[1].equals("<http://www.w3.org/2004/02/skos/core#prefLabel>")){
						//<http://www.w3.org/2004/02/skos/core#prefLabel>は出力しない
					}
					//述語がskos:altLabel
					else if(data[1].equals("<http://www.w3.org/2004/02/skos/core#altLabel>")){
						if(data[2].endsWith("@ja")) {
							bwJAALs[wd_type].write(line+"\n");
							numJAALs[wd_type]++;
						}
						else if(data[2].endsWith("@en")) {
							bwENALs[wd_type].write(line+"\n");
							numENALs[wd_type]++;
							}
					}
					//述語がschema:description
					else if(data[1].equals("<http://schema.org/description>")){
						if(data[2].endsWith("@ja")) {
							bwJADESs[wd_type].write(line+"\n");
							numJADESs[wd_type]++;
						}
						else if(data[2].endsWith("@en")) {
							bwENDESs[wd_type].write(line+"\n");
							numENDESs[wd_type]++;
							}
					}
					//述語がwdt:P31
					else if(data[1].equals("<http://www.wikidata.org/prop/direct/P31>")){
						bwP31s[wd_type].write(line+"\n");
						numP31s[wd_type]++;
					}
					//述語がwdt:P279
					else if(data[1].equals("<http://www.wikidata.org/prop/direct/P279>")){
						bwP279s[wd_type].write(line+"\n");
						numP279s[wd_type]++;
					}
					//目的語がWikidataのEntity（/Prpoerty含む）
					else if(data[2].startsWith("<http://www.wikidata.org/")){
						bwWDOs[wd_type].write(line+"\n");
						numWDOs[wd_type]++;
					}
					//目的語がその他のURL
					else if(data[2].startsWith("<http")){
						bwOUOs[wd_type].write(line+"\n");
						numWDOUs[wd_type]++;
					}
					//述語がその他，目的語がリテラル
					else if(data[2].endsWith("@ja")) {
							bwJAOs[wd_type].write(line+"\n");
							numJAOLs[wd_type]++;
					}
					else if(data[2].endsWith("@en")) {
						bwENOs[wd_type].write(line+"\n");
						numENOLs[wd_type]++;
					}
					else if(data[2].endsWith("\"")) {
						bwOLOs[wd_type].write(line+"\n");
						numOLOs[wd_type]++;
					}
				
						
				}// if(data.length>=3) の終了


			}//while文の終了

			br.close();
			bwOTHER.close();
			
			//各データの行数を出力
			System.out.println("元の行数:\t"+i);
			System.out.println("各トリプル:\tEntity\tProperty\tOther");
			
			System.out.print("ja-label:");
			for(int k=0;k<3;k++) {
				if(k<3)bwJALs[k].close();
				System.out.print("\t"+numJALs[k]);
			}
			System.out.print("\n");					
			System.out.print("en-label:");
			for(int k=0;k<3;k++) {
				if(k<3)bwENLs[k].close();
				System.out.print("\t"+numENLs[k]);
			}
			System.out.print("\n");
			
			System.out.print("ja-altLabel:");
			for(int k=0;k<3;k++) {
				if(k<3)bwJAALs[k].close();
				System.out.print("\t"+numJAALs[k]);
			}
			System.out.print("\n");					
			System.out.print("en-altLabel:");
			for(int k=0;k<3;k++) {
				if(k<3)bwENALs[k].close();
				System.out.print("\t"+numENALs[k]);
			}
			System.out.print("\n");
			
			System.out.print("ja-description:");
			for(int k=0;k<3;k++) {
				if(k<3)bwJADESs[k].close();
				System.out.print("\t"+numJADESs[k]);
			}
			System.out.print("\n");					
			System.out.print("en-description:");
			for(int k=0;k<3;k++) {
				if(k<3)bwENDESs[k].close();
				System.out.print("\t"+numENDESs[k]);
			}
			System.out.print("\n");
			
			System.out.print("P31:");
			for(int k=0;k<3;k++) {
				if(k<3)bwP31s[k].close();
				System.out.print("\t"+numP31s[k]);
			}
			System.out.print("\n");	
			
			System.out.print("P279:");
			for(int k=0;k<3;k++) {
				if(k<3)bwP279s[k].close();
				System.out.print("\t"+numP279s[k]);
			}
			System.out.print("\n");	
			
			System.out.print("wd-other:");
			for(int k=0;k<3;k++) {
				if(k<3)bwWDOs[k].close();
				System.out.print("\t"+numWDOs[k]);
			}
			System.out.print("\n");	
			
			System.out.print("url-other:");
			for(int k=0;k<3;k++) {
				if(k<3)bwOUOs[k].close();
				System.out.print("\t"+numWDOUs[k]);
			}
			System.out.print("\n");	
			
			System.out.print("ja-otherLiteral:");
			for(int k=0;k<3;k++) {
				if(k<3)bwJAOs[k].close();
				System.out.print("\t"+numJAOLs[k]);
			}
			System.out.print("\n");					
			System.out.print("en-otherLiteral:");
			for(int k=0;k<3;k++) {
				if(k<3)bwENOs[k].close();
				System.out.print("\t"+numENOLs[k]);
			}
			System.out.print("\n");
			System.out.print("otherLiteral-otherLiteral:");
			for(int k=0;k<3;k++) {
				if(k<3)bwOLOs[k].close();
				System.out.print("\t"+numOLOs[k]);
			}
			System.out.print("\n");
			
			

			//処理時間を出力
			long endTime = System.currentTimeMillis();
			System.out.println("全体処理時間 ： " + (endTime - startTime) + "ミリ秒");
			
		}
		catch(IOException e) {
			System.out.println("ERROR at line:"+i);
			System.out.println(e.toString());
		}
	}	
}
