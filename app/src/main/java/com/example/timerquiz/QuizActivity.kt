package com.example.timerquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.timerquiz.databinding.ActivityQuizBinding
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.red
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.provider.CalendarContract.Colors
import kotlinx.coroutines.Delay
import kotlin.math.floor

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    private var rightAnswer: String? = null
    private var rightAnswerCount = 0
    private var quizCount = 1
    private val QUIZ_COUNT = 130

    // クイズデータを保持するリスト（問題文、正解、選択肢）
    private val quizData = mutableListOf(
        // クイズデータ（例: 問題文, 正解, 選択肢1, 選択肢2, 選択肢3）
        mutableListOf("日本全国を(モウラ)する。", "網羅", "真浦", "猛裸", "盲螺"),
        mutableListOf("長年保っていた(キンコウ)が破られる。", "均衡", "近郊", "金鉱", "錦江"),
        mutableListOf("差別(テッパイ)には賛成だ。", "撤廃", "鉄杯", "哲俳", "轍胚"),
        mutableListOf("船が波に(ホンロウ)される。", "翻弄", "奔老", "叛楼", "本糧"),
        mutableListOf("高価な(シンジュ)のネックレス。", "真珠", "新受", "心寿", "臣授"),
        mutableListOf("ダムの(カッスイ)で水不足が深刻だ。", "渇水", "活推", "喝酔", "克粋"),
        mutableListOf("日本各地を(ルロウ)する。", "流浪", "瑠楼", "留労", "琉漏"),
        mutableListOf("プロ野球の(カントク)を務める。", "監督", "感得", "完徳", "簡牘"),
        mutableListOf("会社が(ハタン)する。", "破綻", "葉端", "刃単", "覇鍛"),
        mutableListOf("財界と政界の(ユチャク)は問題だ。", "癒着", "油摘", "柚嫡", "由茶苦"),
        mutableListOf("家事を手伝いお(ダチン)をねだる。", "駄賃", "打珍", "陀陳", "堕沈"),
        mutableListOf("(フウリン)は夏の風物詩だ。", "風鈴", "封臨", "諷倫", "不宇凛"),
        mutableListOf("交通(ジュウタイ)にはまる。", "渋滞", "重耐", "従態", "十隊"),
        mutableListOf("罪を犯しても(ゴウモン)は避けるべきだ。", "拷問", "号文", "剛悶", "業聞"),
        mutableListOf("(ジョウモン)時代の土器が発見される。", "縄文", "情問", "状紋", "常門"),
        mutableListOf("通気性に優れた(センイ)で作られた洋服。", "繊維", "宣伊", "選意", "千射"),
        mutableListOf("本棚を(セイトン)する。", "整頓", "制遁", "生豚", "性団"),
        mutableListOf("洗濯には(ナンスイ)が適している。", "軟水", "南推", "難吹", "楠垂"),
        mutableListOf("(ジミ)に富む料理。", "滋味", "地味", "自見", "時実"),
        mutableListOf("(ガクフ)は演奏に欠かせない。", "楽譜", "学府", "岳父", "覚不"),
        mutableListOf("当初の方針を(カンテツ)する。", "貫徹", "関鉄", "肝蛭", "完徹"),
        mutableListOf("領土を(ヘンカン)する。", "返還", "変換", "偏官", "辺間"),
        mutableListOf("スズメバチが(エイソウ)する。", "営巣", "営倉", "詠草", "栄相"),
        mutableListOf("交渉が(ダケツ)した。", "妥結", "妥決", "打欠", "打決"),
        mutableListOf("新人を(カイジュウ)する。", "懐柔", "怪獣", "海獣", "晦渋"),
        mutableListOf("惨劇を(ニョジツ)に物語っている映像だ。", "如実", "女日", "如日", "女実"),
        mutableListOf("疑わしい相手に(カマ)をかける。", "鎌", "窯", "釜", "嘉麻"),
        mutableListOf("(コウケツ)さは厳格な習慣をともなう。", "高潔", "纐纈", "膏血", "高決"),
        mutableListOf("(ゲイゴウ)的な性格はやがて身を滅ぼす。", "迎合", "芸号", "迎業", "芸郷"),
        mutableListOf("昨夜のキユウは一本の電話で(キユウ)に終わった", "杞憂", "希有", "既有", "喜勇"),
        mutableListOf("砂漠を歩きつづけて、彼は水を(カツボウ)した。", "渇望", "克某", "活棒", "勝帽"),
        mutableListOf("事情を(チシツ)した一部の人間による犯行だ。", "知悉", "地質", "血失", "千執"),
        mutableListOf("彼は契約を(ホゴ)にされた。", "反故", "保護", "反古", "補語"),
        mutableListOf("彼はロマン主義に(ケイトウ)していた。", "傾倒", "系統", "継投", "継東"),
        mutableListOf("テストが終わり、自由を(キョウジュ)した。", "享受", "教授", "恭樹", "京樹"),
        mutableListOf("テストが終わっても勉強とは(シュショウ)だ。", "殊勝", "首相", "主将", "主唱"),
        mutableListOf("写真の欠片と思われる(ザンシ)を見つけた。", "残滓", "惨死", "残糸", "慙死"),
        mutableListOf("現代文明がもたらす(オンケイ)を享受する。", "恩恵", "音型", "音形", "恩啓"),
        mutableListOf("夏の高温多湿の気候には(ガマン)できない。", "我慢", "我満", "雅曼", "賀万"),
        mutableListOf("バブルが弾けて冷戦体制が(ホウカイ)した。", "崩壊", "法界", "抱懐", "報介"),
        mutableListOf("(レンサ)的に新しい情報が次々と生まれる。", "連鎖", "錬鎖", "蓮査", "恋紗"),
        mutableListOf("苦労のかいあって仕事が(キドウ)に乗った。", "軌道", "起動", "機動", "気道"),
        mutableListOf("理想を語ると幾多の困難に(ソウグウ)する。", "遭遇", "曹禺", "宗宮", "双偶"),
        mutableListOf("日本の現実を正しく(ハアク)し批判する。", "把握", "刃悪", "葉悪", "派渥"),
        mutableListOf("(アンチュウモサク)する気もち", "暗中模索", "案中摸索", "暗中摸索", "案中模作"),
        mutableListOf("(イキショウチン)する", "意気銷沈", "意奇消沈", "意気性沈", "意気消鎮"),
        mutableListOf("初対面で(イキトウゴウ)する", "意気投合", "異気投合", "意希投合", "意気透合"),
        mutableListOf("(イクドウオン)に唱える", "異口同音", "意口同音", "異句同音", "異口同恩"),
        mutableListOf("(イシンデンシン)の仲", "以心伝心", "以心電信", "以信伝信", "意心伝心"),
        mutableListOf("(ウミセンヤマセン)のつわもの", "海千山千", "海仙山仙", "膿千山栓", "海先山戦"),
        mutableListOf("(エイコセイスイ)は世の習い", "栄枯盛衰", "栄故盛垂", "英枯政衰", "栄枯精粋"),
        mutableListOf("(カンガイムリョウ)の表情", "感慨無量", "灌漑無量", "感慨無料", "館外無量"),
        mutableListOf("(キキュウソンボウ)の瀬戸際", "危急存亡", "希急存亡", "危級存亡", "既急存亡"),
        mutableListOf("(キシカイセイ)の一発", "起死回生", "貴志回生", "起死快晴", "忌死回生"),
        mutableListOf("(ギシンアンキ)になる", "疑心暗鬼", "疑心安鬼", "疑信暗鬼", "疑心暗気"),
        mutableListOf("(シコウサクゴ)を重ねる", "試行錯誤", "施行錯誤", "試行錯誤", "試行索誤"),
        mutableListOf("(サイショクケンビ)の花嫁", "才色兼備", "債色兼備", "才色憲備", "才色兼美"),
        mutableListOf("(キドアイラク)を面に出す", "喜怒哀楽", "喜怒相楽", "喜怒哀絡", "喜弩哀楽"),
        mutableListOf("(キョキョジツジツ)のかけひき", "虚々実々", "拠々実々", "拒々実々", "巨々実々"),
        mutableListOf("自然豊かで(酪農)が盛んだ。", "らくのう", "かくのう", "らくなう", "こうのう"),
        mutableListOf("(彙報)を発行する。", "いほう", "ごほう", "ごほ", "きほう"),
        mutableListOf("(刹那)主義の若輩。", "せつな", "さつな", "さりな", "しな"),
        mutableListOf("息子を(溺愛)する。", "できあい", "ひきあい", "おぼれあい", "れきあい"),
        mutableListOf("盗難事件が(頻発)している。", "ひんぱつ", "ふんぱつ", "はんはつ", "ひんはつ"),
        mutableListOf("作物が(霜害)の被害を受ける。", "そうがい", "しもがい", "あいがい", "しょうがい"),
        mutableListOf("対戦相手を(一蹴)する。", "いっしゅう", "ひとけり", "いっしょう", "きっく"),
        mutableListOf("彼の本性が(露呈)された。", "ろてい", "ろおう", "ろかい", "ろやい"),
        mutableListOf("公の弾劾により(罷免)される。", "ひめん", "のうめん", "むめん", "かいめん"),
        mutableListOf("試作品を(頒布)する。", "はんぷ", "ふんぷ", "ひんぷ", "ほんぷ"),
        mutableListOf("大声で(脅嚇)する。", "きょうかく", "いかく", "きょうふ", "きょうい"),
        mutableListOf("人生の意味を(思索)する。", "しさく", "けんさく", "しさ", "せんさく"),
        mutableListOf("(畏怖)の念を抱く。", "いふ", "きょうふ", "おおふ", "とふ"),
        mutableListOf("(旺盛)な食欲。", "おうせい", "おうもり", "おうはん", "はんせい"),
        mutableListOf("(辣腕)を振るう。", "らつわん", "しつわん", "とげうで", "とげわん"),
        mutableListOf("(苛酷)な状況。", "かこく", "しんこく", "ここく", "こかく"),
        mutableListOf("人生を(諦観)する。", "ていかん", "ていけん", "しけん", "ていこん"),
        mutableListOf("カーテンの(裾)。", "すそ", "えり", "しわ", "しみ"),
        mutableListOf("(凄惨)な戦い。", "せいさん", "すごさん", "しんさん", "そいさん"),
        mutableListOf("(失踪)事件を調査する。", "しっそう", "さっそう", "すっそう", "そっそう"),
        mutableListOf("レーダーで飛行機を(捕捉)する。", "ほそく", "ほかく", "ほたく", "とそく"),
        mutableListOf("(唾棄)すべき行為。", "だき", "ばき", "すいき", "つばき"),
        mutableListOf("知人の出世を(妬む)。", "ねたむ", "いやむ", "せきむ", "くるしむ"),
        mutableListOf("内乱が(勃発)した。", "ぼっぱつ", "ぶっぱつ", "ぼつはつ", "ぼっぷつ"),
        mutableListOf("精神を(陶冶)する。", "とうや", "とうじ", "とうぢ", "とうま"),
        mutableListOf("(臆断)を下す。", "おくだん", "おっだん", "おっくだん", "おくどん"),
        mutableListOf("内閣の(瓦解)。", "がかい", "がかい", "がれき", "がれきかい"),
        mutableListOf("(明瞭)な発音。", "めいりょう", "めいろう", "めいめい", "めいとう"),
        mutableListOf("食物を(玩味)する。", "がんみ", "ぎんみ", "げんみ", "ごんみ"),
        mutableListOf("(賄賂)を贈って買収する。", "わいろ", "ゆうろ", "わいほ", "さいふ"),
        mutableListOf("人を(愚弄)する。", "ぐろう", "ぐほう", "ぐろん", "ぐこう"),
        mutableListOf("(砂嵐)に襲われる。", "すなあらし", "さふう", "すなふう", "さかぜ"),
        mutableListOf("(畏敬)の念を抱く。", "いけい", "けけい", "ふけい", "くけい"),
        mutableListOf("あの人は(語彙)が豊富だ。", "ごい", "ごく", "ごき", "ごえ"),
        mutableListOf("(茨)の道を行く。", "いばら", "いばらき", "つぎ", "とげ"),
        mutableListOf("(均斉)のとれた形。", "きんせい", "きんこう", "きんとう", "きんさい"),
        mutableListOf("有名女子大出身の(才媛)。", "さいえん", "さいえ", "さいう", "さいい"),
        mutableListOf("敵軍の(牙城)に迫る。", "がじょう", "ぎじょう", "ぐじょう", "げじょう"),
        mutableListOf("反乱軍は(潰走)した。", "かいそう", "けいそう", "かんそう", "げきそう"),
        mutableListOf("(俳諧)の編著。", "はいかい", "はいく", "はいけい", "ひかい"),
        mutableListOf("(蓋然)の結果。", "がいぜん", "がぜん", "きぜん", "ぼぜん"),
        mutableListOf("名誉を(毀損)する。", "きそん", "きぞん", "はそん", "くそん"),
        mutableListOf("犯罪者の(巣窟)。", "そうくつ", "すくつ", "そうしゅつ", "そうきゅう"),
        mutableListOf("神社に(詣でる)。", "もうでる", "まうでる", "うまでる", "ひいでる"),
        mutableListOf("友人との間に(間隙)が生じた。", "かんげき", "すきま", "かんすき", "ますき"),
        mutableListOf("(梗概)を示す。", "こうがい", "こうげい", "きんがい", "こっがい"),

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 開始時間を取得し、表示
        var startTime: Int = intent.getIntExtra("TIME_KEY", 0)
        if (startTime != null) {
            binding.time.text = "${startTime/1000}"
        }

        // カウントダウンタイマーの設定と開始
        var timer = object :CountDownTimer(startTime.toLong(), 100){
            //残り時間表示
            override fun onTick(p0: Long) {
                //残り時間を表示
                var minute = floor((p0 / 1000 / 60).toDouble())
                var minute1 = floor(minute / 10)
                var minute2 = floor(minute % 10)
                var second = floor(((p0 / 1000) % 60).toDouble())
                var second1 = floor((second / 10))
                var second2 = floor((second % 10))
                //binding.time.text ="${p0/1000}" //秒単位
                binding.time.text = getString(R.string.time, minute1.toInt(), minute2.toInt() ,second1.toInt(), second2.toInt())
            }
            //終了設定
            override fun onFinish() {
                //TODO("Not yet implemented")
                binding.time.text ="タイムアップ"
                sendResult()
            }
        }

        timer.start()
        // 問題文シャッフル
        quizData.shuffle()
        showNextQuiz()
    }

    // 次のクイズを表示する処理
    fun showNextQuiz() {
        // カウントラベルの更新
        binding.countLabel.text = getString(R.string.count_label, quizCount)

        // クイズを１問取り出す
        val quiz = quizData[0]

        // 問題をセット
        binding.questionLabel.text = quiz[0]

        // 正解をセット
        rightAnswer = quiz[1]

        //  問題文を選択肢配列から削除
        quiz.removeAt(0)

        // 正解と選択肢３つをシャッフル
        quiz.shuffle()

        // 正解と選択肢をセット
        binding.answerBtn1.text = quiz[0]
        binding.answerBtn2.text = quiz[1]
        binding.answerBtn3.text = quiz[2]
        binding.answerBtn4.text = quiz[3]

        // 出題したクイズを削除する
        quizData.removeAt(0)
    }

    // 解答が選択された時の処理
    fun checkAnswer(view: View) {
        // どの解答ボタンが押されたか
        val answerBtn: Button = findViewById(view.id)
        val btnText = answerBtn.text.toString()

        if (btnText == rightAnswer) {
            binding.answerText.text = "〇"
            binding.answerText.setTextColor(Color.RED)
            binding.answer.text = "答え：$rightAnswer"
            rightAnswerCount++
        } else {
            binding.answerText.text = "☓"
            binding.answerText.setTextColor(Color.BLUE)
            binding.answer.text = "答え：$rightAnswer"
        }
        // 指定した秒数間待機
        Handler(Looper.myLooper()!!).also {
            it.postDelayed({
                binding.answerText.text = ""
                binding.answer.text = ""
                quizCount++
                showNextQuiz()
            },1500)
        }
        if(quizCount == QUIZ_COUNT){
            sendResult()
        }
    }

    // 結果をResultActivityに送信
    fun sendResult(){
        val intent = Intent(this@QuizActivity, ResultActivity::class.java)
        intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
        intent.putExtra("QUIZ_COUNT", quizCount)
        startActivity(intent)
    }
}