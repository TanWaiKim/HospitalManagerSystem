/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : hospital

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2018-03-22 13:18:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_admin`
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE `tb_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(20) NOT NULL COMMENT '密码',
  `sex` char(2) DEFAULT NULL COMMENT '性别',
  `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `address` varchar(50) DEFAULT NULL COMMENT '住址',
  `intro` varchar(50) DEFAULT NULL COMMENT '个人简介',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_admin
-- ----------------------------
INSERT INTO `tb_admin` VALUES ('1', '小白', '123456', '男', '254689568@qq.com', '15458968957', '北京市海定区', '啦啦啦啦啦', null, null);

-- ----------------------------
-- Table structure for `tb_drug`
-- ----------------------------
DROP TABLE IF EXISTS `tb_drug`;
CREATE TABLE `tb_drug` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `drugtype_id` int(11) NOT NULL COMMENT '所属类别',
  `drug_name` varchar(50) DEFAULT NULL COMMENT '医药名称',
  `drug_no` varchar(30) DEFAULT NULL COMMENT '批准文号',
  `purpose` varchar(255) DEFAULT NULL COMMENT '功能主治',
  `spec` varchar(50) DEFAULT NULL COMMENT '规格',
  `unit` char(4) DEFAULT NULL COMMENT '单位',
  `howuse` varchar(255) DEFAULT NULL COMMENT '用法用量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `sale_price` decimal(20,2) DEFAULT NULL COMMENT '销药单价',
  `uneffect` varchar(255) DEFAULT NULL COMMENT '不良反应',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_drug
-- ----------------------------
INSERT INTO `tb_drug` VALUES ('1', '1', '阿司匹林肠溶片', '国药准字H15122543', '本品具有显著的解热镇痛作用，能使发热者的体温降低到正常，而对体温正常者一般无影响。其镇痛作用对轻，中度体表疼痛，尤其是炎症性疼痛有明显疗效.临床常用于感冒发热头痛，偏头痛，牙痛，神经痛，关节痛，肌肉痛和痛经等。', '100片/每瓶', '瓶', '1．成人常用量  口服，一次0.3-0.6g，一日3次，必要时每4小时1次。2．小儿常用量  口服，每日按体表面积1．5g/m2，分4-6次口服，或每次按体重5-10mg，或每次每岁60mg，必要时4-6小时1次。', '2018-03-24 03:21:36', '2018-03-21 19:14:22', null, '表现为头痛，头晕，耳鸣，视力障碍，出汗，精神恍惚，恶心，呕吐等，甚至出现惊厥和昏迷。');
INSERT INTO `tb_drug` VALUES ('2', '1', '去痛片', '国药准字H16020382', '本品用于发热及轻、中度的疼痛。', '12片*2板/盒', '盒', '需要时服用 一次1-2片，一日1-3次。', '2018-03-22 11:49:23', '2018-03-21 17:24:03', '2.00', '服用氨基比林可有呕吐、皮疹、发热、大量出汗及发生口腔炎等，少数可致中性粒细胞缺乏、再生障碍性贫血、渗出性红斑、剥脱性皮炎等。');
INSERT INTO `tb_drug` VALUES ('3', '1', '吲哚美辛肠溶片', '国药准字H14205326', '本品用于： 1. 关节炎，可缓解疼痛和肿胀； 2. 软组织损伤和炎症； 3. 解热； 4. 其他：用于治疗偏头痛。', '100片/盒', '盒', '1.成人常用量一次25～50mg，一日2～3次，一日最大量不应超过150mg；2.小儿常用量：一日按体重1.5～2.5mg/kg，分3～4次。', '2018-03-21 21:58:38', '2018-03-19 21:00:55', '25.00', '①胃肠道：出现消化不良、胃痛、胃烧灼感、恶心反酸等症状，出现溃疡、胃出血及胃穿孔。②神经系统：出现头痛、头晕、焦虑及失眠等，严重者可有精神行为障碍或抽搐等。');
INSERT INTO `tb_drug` VALUES ('4', '2', '复方对乙酰氨基酚片', '国药准字H11021706', '1.本品用于普通感冒或流行性感冒引起的发热. 2.也用于缓解轻至中度疼痛如头痛﹑关节痛﹑偏头痛﹑牙痛﹑肌肉痛﹑神经痛﹑痛经。', '铝塑泡罩包装，12片/板', '板', '口服，成人一次1片，若持续发热或疼痛，可间隔4-6小时重复用药一次，24小时内不超过4次。', '2018-03-21 23:00:28', '2018-03-19 17:11:37', '31.00', '1.较常见的有恶心、呕吐、上腹部不适或疼痛等胃肠道反应。 2.较少见或罕见的有胃肠道出血或溃疡，多见于大剂量服用本品的患者。');
INSERT INTO `tb_drug` VALUES ('5', '2', '氯酚黄敏', '国药准字H11021926', '用于感冒引起的头痛、发热、鼻塞、流涕、咽痛、痰多等症。', '药品包装用铝箔，每板20片。', '板', '口服，一次1～2片，一日3次或遵医嘱。', '2018-03-21 23:16:01', '2018-03-19 19:22:20', '35.00', '1，用药后发生儿童血尿的病例报道较多；其次为胃不适，烧灼感；此外尚有头痛、头晕、嗜睡，以及皮疹、心悸、胸闷、咽喉痛等不良反应。');
INSERT INTO `tb_drug` VALUES ('6', '2', '999感冒灵颗粒', '国药准字Z15021940', '解热镇痛。本品用于感冒引起的头痛，发热，鼻塞，流涕，咽痛。', '10g*9袋', '袋', '开水冲服，一次10克，一日3次。', '2018-03-19 15:24:34', '2018-03-21 15:42:29', '20.00', '1.偶见皮疹﹑荨麻疹﹑药热及粒细胞减少; 2.可见困倦﹑嗜睡﹑口渴﹑虚弱感; 3.长期大量用药会导致肝肾功能异常。');
INSERT INTO `tb_drug` VALUES ('7', '3', '蛇胆川贝液', '国药准字Z13021983', '本品用于祛风止咳，除痰散结。用于急性支气管炎和慢性支气管炎中的风热咳嗽，痰多，气喘，胸闷，咳痰不爽或久咳不止。', '本品每支装10ml', '支', '本品口服。一次10 毫升，一日2次。', '2018-03-19 15:28:31', '2018-03-21 15:49:30', '2.00', '尚不明确。');
INSERT INTO `tb_drug` VALUES ('8', '3', '枸橼酸喷托维林片', '国药准字H12022140', '急性支气管炎,慢性支气管炎,用于各种原因引起的干咳。', '25mg/片', '片', '1.成人常用量：口服，一次25mg，一日3—4次。 2.小儿常用量：口服，5岁以上一次6.25—12.5mg，一日2—3次。', '2018-03-19 15:31:16', '2018-03-19 15:31:16', null, '偶有便秘、轻度头痛、头晕、嗜睡、口干、恶心、腹胀、皮肤过敏等反应。');
INSERT INTO `tb_drug` VALUES ('9', '4', '诺氟沙星胶囊', '国药准字H26021336', '适用于敏感菌所致的尿路感染、淋病、前列腺炎、肠道感染和伤寒及其他沙门菌感染。', '0.1g/粒', '粒', '1.大肠埃希菌﹑肺炎克雷伯菌及奇异变形菌所致的急性单纯性下尿路感染：一次400mg,一日2次,疗程3日。 2.其他病原菌所致的单纯性尿路感染：剂量同上,疗程7～10日。', '2018-03-19 15:34:05', '2018-03-19 19:39:02', '30.00', '1．胃肠道反应 较为常见，可表现为腹部不适或疼痛、腹泻、恶心或呕吐。 2．中枢神经系统反应 可有头昏、头痛、嗜睡或失眠。 3．过敏反应 皮疹、皮肤瘙痒，偶可发生渗出性多性红斑及血管神经性水肿。少数患者有光敏反应。');
INSERT INTO `tb_drug` VALUES ('10', '4', '氯霉素片', '国药准字H32620326', '1.伤寒和其他沙门菌属感； 2.耐氨苄西林的B型流感嗜血杆菌脑膜炎或对青霉素过敏患者的肺炎链球菌； 3.脑脓肿，尤其耳源性，常为需氧菌和厌氧菌混合感染等。', '0.25g/片', '片', '口服。 1.成人一日1.5～3g，分3～4次服用。 2.小儿按体重一日25～50mg/kg，分3～4次服用。 3.新生儿一日不超过25mg/kg，分4次服用。', '2018-03-19 15:39:22', '2018-03-21 17:14:34', '12.00', '1.对造血系统的毒性反应是氯霉素最严重的不良反应；2.溶血性贫血，可发生在某些先天性葡萄糖-6-磷酸脱氢酶不足的患者。');
INSERT INTO `tb_drug` VALUES ('11', '5', '溴丙胺太林片', '国药准字H53020321', '神经源性膀胱,手部血管损伤,食管过短,短食管,沙门氏菌属食物中毒,非伤寒沙门氏菌感染,倾倒综合征,胃切除术后综合征,早期餐后症状群,吡唑酮类中毒,胃肠痉挛性疼痛。', '15毫克*100片', '片', '口服。成人一次1片，疼痛时服。必要时4小时后可重复1次。', '2018-03-19 15:41:34', '2018-03-19 19:40:56', '55.00', '常见口干、面红、视力模糊、尿潴留、便秘、头痛、心悸等，减量或停药后可消失。');
INSERT INTO `tb_drug` VALUES ('12', '6', '多潘立酮片', '国药准字H20610002', '用于消化不良、腹胀、嗳气、恶心、呕吐、腹部胀痛。', '10mg*30片', '片', '成人：口服，一次10-20ml，一日3次，餐前服；儿童：口服，按体重一次200-400ug/Kg，每8小时1次。', '2018-03-19 15:43:30', '2018-03-19 19:45:07', '20.00', '1.偶见轻度腹部痉挛、口干、皮疹、头痛、腹泻、神经过敏、倦耽嗜睡、头晕等。|2.有时导致血清泌乳素水平升高、溢乳、男子乳房女性化等，但停药后即可恢复正常。');
INSERT INTO `tb_drug` VALUES ('13', '7', '大黄碳酸氢钠片', '国药准字H53021604', '用于食欲缺乏、胃酸过多。', '1000片/瓶', '瓶', '口服，一次1～3片，一日3次。', '2018-03-19 15:46:25', '2018-03-19 19:49:02', '50.00', '偶见轻度恶心。');
INSERT INTO `tb_drug` VALUES ('14', '7', '开塞露(含甘油)', '国药准字H12320839', '用于便秘，胃肠食物不耐受症。', '20ml/支', '支', '将容器顶端刺破或剪开，涂以油脂少许，缓慢插入肛门，然后将药液挤入直肠内，成人一次1支，儿童一次0.5支。', '2018-03-19 15:49:03', '2018-03-21 15:30:23', '30.00', '尚不明确。');
INSERT INTO `tb_drug` VALUES ('15', '8', '藿香正气水', '国药准字Z23020063', '本品适用于解表化湿，理气和中。用于外感风寒、内伤湿滞或夏伤暑湿所致的感冒，症见头痛昏重、胸膈痞闷、脘腹胀痛、呕吐泄泻;胃肠型感冒见上述证候者。', '每支装10毫升', '支', '本品口服。一次5~10毫升，一日2次，用时摇匀。', '2018-03-20 05:50:14', '2018-03-21 14:45:38', '12.00', '1.近年来，应用藿香正气水出现的过敏反应，应引起重视。2.有报道服用藿香正气水致荨麻疹样药疹、上消化道出血、过敏性紫癜以及低血糖。');
INSERT INTO `tb_drug` VALUES ('16', '9', '氯雷他定片', '国药准字H30070030', '用于缓解过敏性鼻炎有关的症状,如喷嚏、流涕、鼻痒、鼻塞以及眼部痒及烧灼感。口服药物后,鼻和眼部症状及体征得以迅速缓解。亦适用于缓解慢性荨麻疹.瘙痒性皮肤病及其他过敏性皮肤病的症状及体征。', '10mg*6片', '盒', '口服。1.成人及12岁以上儿童：一日1次，一次1片(10毫克)。2.2～12岁儿童：体重＞30公斤：一日1次，一次1片(10毫克)。体重≤30公斤：一日1次，一次半片(10毫克)。', '2018-03-20 05:52:45', '2018-03-19 20:03:14', '30.00', '1•在每天10毫克的推荐剂量下.本品未见明显的镇静作用.其发生率与安慰剂相似。|2•常见不良反应有乏力、头痛、嗜睡、口干、胃肠道不适包括恶心.胃炎以及皮疹等。');
INSERT INTO `tb_drug` VALUES ('17', '10', '复方三氯叔丁醇气雾剂', '国药准字H02321909', '外用消炎镇痛药。用于一般灼伤、擦伤、日光晒伤、虫咬等。', '500ml/瓶', '瓶', '外用，将药液摇匀，倒置，对准患处，于适当距离处揿压喷头，使药液喷于患处成一薄层。一日2～3次。', '2018-03-19 15:56:48', '2018-03-19 20:04:17', '30.00', '1.药液对眼结膜有较大刺激。2.偶可引起接触性皮炎。');
INSERT INTO `tb_drug` VALUES ('18', '11', '叶酸多维营养素1', '国药准字Z20053863', '孕妇早期营养品', '0.5克*60粒', '瓶', '每日2次,每次1片,或每日1次,每次2片,随餐食用更佳。', '2018-03-19 16:01:05', '2018-03-21 14:59:52', '30.00', '尚不明确。');
INSERT INTO `tb_drug` VALUES ('19', '12', '维生素K1注射液', '国药准字H32026904', '本品用于维生素K缺乏引起的出血，如梗阻性黄疸、胆瘘、慢性腹泻等所致出血等。', '1ml:10mg', '瓶', '低凝血酶原血症:肌内或深部皮下注射，每次10mg，每日1～2次，24小时内总量不超过40mg。', '2018-03-19 16:06:37', '2018-03-19 20:13:07', '30.00', '偶见过敏反应。，肌注可引起局部红肿和疼痛。');
INSERT INTO `tb_drug` VALUES ('20', '13', '利巴韦林片', '国药准字H10269521', '本品适用于呼吸道合胞病毒引起的病毒性肺炎与支气管炎，皮肤疱疹病毒感染。', '50mg/片', '片', '口服。病毒性呼吸道感染:成人一次0.15g，一日3次，疗程7天。', '2018-03-19 16:09:09', '2018-03-19 20:06:21', '35.00', '常见的不良反应有贫血﹑乏力等，停药后即消失。');
INSERT INTO `tb_drug` VALUES ('21', '14', '注射用盐酸哌甲酯', '国药准字H11021926', '本品中枢兴奋药。适用于消除催眠药引起的嗜睡﹑倦怠及呼吸抑制。近年来用于治疗小儿轻微脑功能失调。', '20mg/支', '支', '皮下，肌内注射或缓慢静脉注射。一次10～20mg。', '2018-03-19 16:12:02', '2018-03-21 15:01:42', '30.00', '失眠﹑眩晕﹑头晕﹑头痛﹑恶心﹑厌食﹑心悸﹑口干﹑皮疹﹑运动障碍等。');

-- ----------------------------
-- Table structure for `tb_drugtype`
-- ----------------------------
DROP TABLE IF EXISTS `tb_drugtype`;
CREATE TABLE `tb_drugtype` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `drugtype_name` varchar(30) NOT NULL COMMENT '医药种类',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_drugtype
-- ----------------------------
INSERT INTO `tb_drugtype` VALUES ('1', '解热镇痛药', '解热镇痛药为一类具有解热、镇痛药理作用，同时还有显著抗炎、抗风湿作用的药物。', '2018-03-19 10:16:25', '2018-03-19 16:46:24');
INSERT INTO `tb_drugtype` VALUES ('2', '治感冒类药', '治感冒类药用于治疗感冒的各种药，包括中成药，汤剂，西药，冲剂等等。', '2018-03-19 10:17:32', '2018-03-19 10:31:52');
INSERT INTO `tb_drugtype` VALUES ('3', '止咳化痰药', '止咳化痰药用于养阴润肺，化痰止咳。用于肺肾阴虚，干咳少痰，咽干喉痛。', '2018-03-19 10:18:14', '2018-03-19 10:32:10');
INSERT INTO `tb_drugtype` VALUES ('4', '抗生素', '抗生素主要是由细菌、霉菌或其他微生物产生的次级代谢产物或人工合成的类似物。', '2018-03-19 10:19:00', '2018-03-19 10:32:18');
INSERT INTO `tb_drugtype` VALUES ('5', '胃肠解痉药', '胃肠解痉药又称抑制胃肠动力药，主要为M受体拮抗药，包括颠茄生物碱类及衍生物和大量人工合成代用品。', '2018-03-19 10:19:47', '2018-03-19 10:32:45');
INSERT INTO `tb_drugtype` VALUES ('6', '助消化药', '助消化药用于帮助消化作用的一类药的统称，包含胃蛋白酶、胰酶、乳酶生等。', '2018-03-19 10:21:01', '2018-03-19 16:46:13');
INSERT INTO `tb_drugtype` VALUES ('7', '通便药', '通便药能增加肠内水分，促进肠蠕动、软化粪便、润滑肠道、可促进排便的药物，主要用于治疗功能性便秘。', '2018-03-19 10:22:04', '2018-03-19 10:32:41');
INSERT INTO `tb_drugtype` VALUES ('8', '止泻药', '止泻药指控制腹泻的药物。通过减少肠道蠕动或保护肠道免受刺激而达到止泻作用。适用于剧烈腹泻或长期慢性腹泻，以防止机体过度脱水、水盐代谢失调、消化及营养障碍。', '2018-03-19 10:22:42', '2018-03-19 10:32:54');
INSERT INTO `tb_drugtype` VALUES ('9', '抗过敏药', '抗过敏药主要指抗组胺类药物，口服后吸收很快，能在15—30分钟内使过敏症状迅速得到改善。', '2018-03-19 10:23:46', '2018-03-19 10:33:03');
INSERT INTO `tb_drugtype` VALUES ('10', '外用消炎消毒药', '外用消炎消毒药可用于皮肤局部受伤地方进行消毒、消炎处理。', '2018-03-19 10:27:13', '2018-03-19 10:33:22');
INSERT INTO `tb_drugtype` VALUES ('11', '营养药', '营养药指以补充人体所需的营养成分为目的的药物，指通过消化道外或内的各种途径为患者提供较为全面的机体所需的各种营养物质，以期达到预防或纠正营养不良、增强患者对感染、创伤等应激的耐受力、减少并发症、减少费用、改善患者的临床结局的目的。', '2018-03-19 10:29:06', '2018-03-19 10:33:30');
INSERT INTO `tb_drugtype` VALUES ('12', '促凝血药', '促凝血药指能加速血液凝固或降低毛细血管通透性，促使出血停止的药物，又称止血药。用于治疗出血性疾病。', '2018-03-19 10:30:24', '2018-03-19 10:33:40');
INSERT INTO `tb_drugtype` VALUES ('13', '抗病毒药', '抗病毒药是一类用于预防和治疗病毒感染的药物。在体外可抑制病毒复制酶，在感染细胞或动物体抑制病毒复制或繁殖，在临床上治疗病毒病有效的药物。', '2018-03-19 10:31:36', '2018-03-19 10:31:36');
INSERT INTO `tb_drugtype` VALUES ('14', '中枢兴奋药', '中枢兴奋药是能提高中枢神经系统机能活动的药物。根据中枢兴奋药的作用部位不同，分为大脑兴奋药、脑干兴奋药、脊髓兴奋药。', '2018-03-19 10:35:52', '2018-03-19 10:35:52');

-- ----------------------------
-- Table structure for `tb_provider`
-- ----------------------------
DROP TABLE IF EXISTS `tb_provider`;
CREATE TABLE `tb_provider` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `provider_name` varchar(255) DEFAULT NULL COMMENT '供药商名称',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `contact` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL COMMENT '联络电话',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_provider
-- ----------------------------
INSERT INTO `tb_provider` VALUES ('1', '杭州药惠医药有限公司', '浙江省杭州市萧山区迎宾路1号', '李白', '13658964526', '第一次合作。', '2018-03-22 03:59:54', '2018-03-21 15:20:23');
INSERT INTO `tb_provider` VALUES ('2', '浙江诚信诚意药业有限公司', '浙江省温州市民治区民主路2号', '陈光', '15879653269', '这是一家诚信的公司。', '2018-03-19 06:03:00', '2018-03-18 16:09:35');
INSERT INTO `tb_provider` VALUES ('3', '河南省十势制药股份有限公司', '河南省信阳市淮滨县中山路3号', '刘德华', '13659874589', '医药按时补给，很好。', '2018-03-18 16:06:37', '2018-03-18 16:06:37');
INSERT INTO `tb_provider` VALUES ('4', '海南小洲药业有限公司', '海南省文昌市文东路4号', '谭小白', '15689653624', '这是老客户了', '2018-03-18 16:09:17', '2018-03-18 16:09:17');
INSERT INTO `tb_provider` VALUES ('5', '广州康复制药股份有限公司', '广东省广州市天河区大新路大学路5号', '张艺牟', '15874523620', '新客户，有待观察。', '2018-03-18 16:11:52', '2018-03-18 16:11:52');
INSERT INTO `tb_provider` VALUES ('6', '广东惠民制药有限公司', '广东省深圳市罗湖区罗盘6路', '庄天天', '15246539827', '该公司提供的医药实惠。', '2018-03-19 06:13:58', '2018-03-21 19:11:50');
INSERT INTO `tb_provider` VALUES ('7', '桂林三金制药股份有限公司', '广西省桂林市金新路7号', '庞欣欣', '19658745239', '很不错，以后多合作。', '2018-03-18 16:16:45', '2018-03-18 16:16:45');
INSERT INTO `tb_provider` VALUES ('8', '佛山顺民制药股份有限公司', '广东省佛山市禅城区江湾路10008号', '范成大', '16532985476', '老客户，讲究诚信。', '2018-03-18 16:19:47', '2018-03-18 16:19:47');
INSERT INTO `tb_provider` VALUES ('9', '北京康宝惠民制药有限公司', '北京市顺义区', '王五', '19685326509', '惠民药品，值得进购。', '2018-03-18 16:21:15', '2018-03-18 16:21:15');
INSERT INTO `tb_provider` VALUES ('10', '潮汕仁爱制药股份有限公司', '广东省汕头市潮汕市天柱路12号', '陈小小', '16985458635', '新供应商，供货速度够快。', '2018-03-18 16:22:52', '2018-03-18 16:22:52');

-- ----------------------------
-- Table structure for `tb_purchase`
-- ----------------------------
DROP TABLE IF EXISTS `tb_purchase`;
CREATE TABLE `tb_purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `purchase_no` varchar(20) NOT NULL COMMENT '进药单编号',
  `provider_id` int(11) DEFAULT NULL COMMENT '供应商',
  `total_quantity` int(11) DEFAULT NULL COMMENT '总数量',
  `total_price` decimal(20,2) DEFAULT NULL COMMENT '总价格',
  `operator` varchar(20) DEFAULT NULL COMMENT '操作员',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_purchase
-- ----------------------------
INSERT INTO `tb_purchase` VALUES ('1', 'CGYY152144951666117', '1', '750', '16800.00', '小白', '第一次采购', '2017-11-19 16:51:56', '2017-11-19 16:51:56');
INSERT INTO `tb_purchase` VALUES ('2', 'CGYY152145854086029', '2', '860', '13200.00', '小白', '第二次采购', '2017-11-19 19:22:20', '2017-11-19 19:22:20');
INSERT INTO `tb_purchase` VALUES ('3', 'CGYY152145954261168', '3', '1050', '28250.00', '小白', '第三次采购', '2017-12-01 19:39:02', '2017-12-01 19:39:02');
INSERT INTO `tb_purchase` VALUES ('4', 'CGYY152145990769718', '4', '700', '16500.00', '小白', '第四次采购', '2017-12-20 19:45:07', '2017-12-20 19:45:07');
INSERT INTO `tb_purchase` VALUES ('5', 'CGYY152146022590479', '5', '500', '7100.00', '小白', '第五次采购', '2018-01-01 19:50:25', '2018-01-01 19:50:25');
INSERT INTO `tb_purchase` VALUES ('6', 'CGYY152146092324855', '6', '160', '2400.00', '小白', '第六次采购', '2018-01-01 20:02:03', '2018-01-01 20:02:03');
INSERT INTO `tb_purchase` VALUES ('7', 'CGYY152146099437243', '7', '400', '10400.00', '小白', '第七次采购', '2018-02-15 20:03:14', '2018-02-15 20:03:14');
INSERT INTO `tb_purchase` VALUES ('8', 'CGYY152146105773441', '8', '100', '2500.00', '小白', '第八次采购', '2018-02-20 20:04:17', '2018-02-20 20:04:17');
INSERT INTO `tb_purchase` VALUES ('9', 'CGYY152146112853176', '9', '500', '9900.00', '小白', '第九次采购', '2018-03-19 20:05:28', '2018-03-19 20:05:28');
INSERT INTO `tb_purchase` VALUES ('10', 'CGYY152146118181653', '10', '1250', '32600.00', '小白', '第十次采购', '2018-03-19 20:06:21', '2018-03-19 20:06:21');
INSERT INTO `tb_purchase` VALUES ('11', 'CGYY152161559277062', '9', '222', '3112.00', '小白', '测试数据', '2018-03-21 14:59:52', '2018-03-21 14:59:52');

-- ----------------------------
-- Table structure for `tb_purchase_item`
-- ----------------------------
DROP TABLE IF EXISTS `tb_purchase_item`;
CREATE TABLE `tb_purchase_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `purchase_no` varchar(20) NOT NULL COMMENT '进货单序列化',
  `drug_id` int(11) DEFAULT NULL COMMENT '医药id',
  `drug_name` varchar(50) DEFAULT NULL COMMENT '医药名称',
  `purchase_price` decimal(20,2) DEFAULT NULL COMMENT '进药单价',
  `sale_price` decimal(20,2) DEFAULT NULL COMMENT '销药单价',
  `quantity` int(11) DEFAULT NULL COMMENT '进药数量',
  `purchase_total_price` decimal(20,2) DEFAULT NULL COMMENT '进药总价',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` varchar(255) DEFAULT NULL COMMENT '入库状态',
  `batch_no` varchar(10) DEFAULT NULL,
  `produce_time` datetime DEFAULT NULL,
  `valid_time` datetime DEFAULT NULL,
  `warehouse_no` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_purchase_item
-- ----------------------------
INSERT INTO `tb_purchase_item` VALUES ('1', 'CGYY152144951666117', '1', '阿司匹林肠溶片', '19.00', '24.00', '100', '1000.00', '2017-11-19 16:51:56', '2017-11-19 17:18:44', '已入库', '20140303', '2014-03-03 00:00:00', '2018-03-03 00:00:00', 'CK152136160122306');
INSERT INTO `tb_purchase_item` VALUES ('2', 'CGYY152144951666117', '2', '去痛片', '12.00', '13.00', '150', '1800.00', '2018-11-19 17:07:22', '2018-11-19 17:18:50', '已入库', '20170119', '2017-01-19 00:00:00', '2019-01-19 00:00:00', 'CK152136160122306');
INSERT INTO `tb_purchase_item` VALUES ('3', 'CGYY152144951666117', '3', '吲哚美辛肠溶片', '25.00', '30.00', '200', '5000.00', '2017-11-19 17:08:15', '2017-11-19 17:18:53', '已入库', '20150315', '2015-03-15 00:00:00', '2018-03-15 00:00:00', 'CK152136160122306');
INSERT INTO `tb_purchase_item` VALUES ('4', 'CGYY152144951666117', '4', '复方对乙酰氨基酚片', '30.00', '31.00', '300', '9000.00', '2017-11-19 17:11:37', '2017-11-19 17:18:58', '已入库', '20160919', '2016-09-19 00:00:00', '2022-09-19 00:00:00', 'CK152136160122306');
INSERT INTO `tb_purchase_item` VALUES ('5', 'CGYY152145854086029', '5', '氯酚黄敏', '30.00', '35.00', '160', '4800.00', '2017-11-19 19:22:20', '2017-11-19 19:27:29', '已入库', '20150728', '2015-07-28 00:00:00', '2019-07-28 00:00:00', 'CK152136160201236');
INSERT INTO `tb_purchase_item` VALUES ('6', 'CGYY152145854086029', '6', '999感冒灵颗粒', '10.00', '12.00', '300', '3000.00', '2017-11-19 19:23:56', '2017-11-19 19:27:34', '已入库', '20160215', '2016-02-15 00:00:00', '2019-02-15 00:00:00', 'CK152136160201236');
INSERT INTO `tb_purchase_item` VALUES ('7', 'CGYY152145854086029', '7', '蛇胆川贝液', '12.00', '15.00', '200', '2400.00', '2017-11-19 19:24:44', '2017-11-19 19:27:37', '已入库', '20160307', '2016-03-07 00:00:00', '2018-03-07 00:00:00', 'CK152136160201236');
INSERT INTO `tb_purchase_item` VALUES ('8', 'CGYY152145854086029', '8', '枸橼酸喷托维林片', '15.00', '20.00', '200', '3000.00', '2017-11-19 19:25:25', '2017-11-19 19:27:46', '已入库', '20170619', '2017-06-19 00:00:00', '2020-06-19 00:00:00', 'CK152136160201236');
INSERT INTO `tb_purchase_item` VALUES ('9', 'CGYY152145954261168', '9', '诺氟沙星胶囊', '25.00', '30.00', '500', '12500.00', '2017-12-01 19:39:02', '2017-12-01 19:41:06', '已入库', '20161020', '2016-10-20 00:00:00', '2021-10-20 00:00:00', 'CK152136160296498');
INSERT INTO `tb_purchase_item` VALUES ('10', 'CGYY152145954261168', '10', '氯霉素片', '15.00', '20.00', '250', '3750.00', '2017-12-01 19:39:52', '2017-12-01 19:41:08', '已入库', '20160415', '2016-04-15 00:00:00', '2018-04-15 00:00:00', 'CK152136160296498');
INSERT INTO `tb_purchase_item` VALUES ('11', 'CGYY152145954261168', '11', '溴丙胺太林片', '50.00', '55.00', '200', '10000.00', '2017-12-01 19:40:56', '2017-12-01 19:41:11', '已入库', '20170819', '2017-08-19 00:00:00', '2019-08-19 00:00:00', 'CK152136160296498');
INSERT INTO `tb_purchase_item` VALUES ('12', 'CGYY152145990769718', '12', '多潘立酮片', '15.00', '20.00', '500', '7500.00', '2017-12-20 19:45:07', '2017-12-20 20:10:00', '已入库', '20150215', '2015-02-15 00:00:00', '2018-02-28 00:00:00', 'CK152136160296498');
INSERT INTO `tb_purchase_item` VALUES ('13', 'CGYY152145990769718', '13', '大黄碳酸氢钠片', '45.00', '50.00', '200', '9000.00', '2017-12-20 19:49:02', '2017-12-20 20:10:03', '已入库', '20160103', '2016-01-03 00:00:00', '2018-01-03 00:00:00', 'CK152136160296498');
INSERT INTO `tb_purchase_item` VALUES ('14', 'CGYY152146022590479', '14', '开塞露(含甘油)', '10.00', '15.00', '250', '2500.00', '2018-01-01 19:50:25', '2018-01-01 19:59:45', '已入库', '20161212', '2016-12-12 00:00:00', '2018-05-12 00:00:00', 'CK152136170963482');
INSERT INTO `tb_purchase_item` VALUES ('15', 'CGYY152146092324855', '15', '藿香正气水', '15.00', '20.00', '160', '2400.00', '2018-01-01 20:02:03', '2018-01-01 20:09:12', '已入库', '20150331', '2015-03-31 00:00:00', '2018-04-01 00:00:00', 'CK152136175056922');
INSERT INTO `tb_purchase_item` VALUES ('16', 'CGYY152146099437243', '16', '氯雷他定片', '26.00', '30.00', '400', '10400.00', '2018-02-15 20:03:14', '2018-02-15 20:09:06', '已入库', '20160815', '2016-08-15 00:00:00', '2018-04-15 00:00:00', 'CK152136160122306');
INSERT INTO `tb_purchase_item` VALUES ('17', 'CGYY152146105773441', '17', '复方三氯叔丁醇气雾剂', '25.00', '30.00', '100', '2500.00', '2018-02-20 20:04:17', '2018-02-20 20:07:26', '已入库', '20160201', '2016-02-01 00:00:00', '2018-06-19 00:00:00', 'CK152136160201236');
INSERT INTO `tb_purchase_item` VALUES ('18', 'CGYY152146112853176', '18', '叶酸多维营养素1', '12.00', '15.00', '200', '2400.00', '2018-03-19 20:05:28', '2018-03-19 20:07:21', '已入库', '20161016', '2016-10-16 00:00:00', '2019-10-16 00:00:00', 'CK15213616026498');
INSERT INTO `tb_purchase_item` VALUES ('19', 'CGYY152146118181653', '20', '利巴韦林片', '25.00', '35.00', '400', '10000.00', '2018-03-19 20:06:21', '2018-03-19 20:07:13', '已入库', '20140919', '2014-09-19 00:00:00', '2018-03-19 00:00:00', 'CK152136170963482');
INSERT INTO `tb_purchase_item` VALUES ('20', 'CGYY152146118181653', '19', '维生素K1注射液', '28.00', '30.00', '250', '7000.00', '2018-03-19 20:13:07', '2018-03-19 20:14:23', '已入库', '20160228', '2016-02-28 00:00:00', '2018-03-28 00:00:00', 'CK152136170963482');
INSERT INTO `tb_purchase_item` VALUES ('21', 'CGYY152146118181653', '21', '注射用盐酸哌甲酯', '26.00', '30.00', '600', '15600.00', '2018-03-19 20:14:18', '2018-03-19 20:14:26', '已入库', '20150501', '2015-05-01 00:00:00', '2018-04-28 00:00:00', 'CK152136170963482');
INSERT INTO `tb_purchase_item` VALUES ('22', 'CGYY152146112853176', '1', '阿司匹林肠溶片', '25.00', '24.00', '300', '7500.00', '2017-12-01 20:39:41', '2017-12-01 20:40:04', '已入库', '20171211', '2017-12-11 00:00:00', '2019-12-11 00:00:00', 'CK15213616026498');
INSERT INTO `tb_purchase_item` VALUES ('23', 'CGYY152145954261168', '1', '阿司匹林肠溶片', '20.00', '24.00', '100', '2000.00', '2018-03-19 20:51:20', '2018-03-19 20:51:26', '已入库', '20160429', '2016-04-29 00:00:00', '2018-04-29 00:00:00', 'CK152136160296498');
INSERT INTO `tb_purchase_item` VALUES ('24', 'CGYY152146022590479', '2', '去痛片', '18.00', '20.00', '200', '3600.00', '2018-03-19 21:00:09', '2018-03-19 21:00:09', '待入库', '20170508', '2017-05-08 00:00:00', '2019-05-08 00:00:00', 'CK152136170963482');
INSERT INTO `tb_purchase_item` VALUES ('25', 'CGYY152146022590479', '3', '吲哚美辛肠溶片', '20.00', '25.00', '50', '1000.00', '2018-03-19 21:00:55', '2018-03-19 21:00:55', '待入库', '20170912', '2017-09-12 00:00:00', '2021-09-12 00:00:00', 'CK152136170963482');
INSERT INTO `tb_purchase_item` VALUES ('26', 'CGYY152161559277062', '18', '叶酸多维营养素1', '20.00', '30.00', '50', '1000.00', '2018-03-21 14:59:52', '2018-03-21 14:59:52', '待入库', '20160321', '2016-03-21 00:00:00', '2021-11-21 00:00:00', 'CK152136164657391');
INSERT INTO `tb_purchase_item` VALUES ('27', 'CGYY152161559277062', '21', '注射用盐酸哌甲酯', '30.00', '30.00', '50', '1500.00', '2018-03-21 15:01:42', '2018-03-21 15:01:42', '待入库', '20161121', '2016-11-21 00:00:00', '2018-11-21 00:00:00', 'CK152136160296498');
INSERT INTO `tb_purchase_item` VALUES ('28', 'CGYY152161559277062', '6', '999感冒灵颗粒', '10.00', '20.00', '50', '500.00', '2018-03-21 15:42:29', '2018-03-21 15:42:29', '待入库', '20180319', '2018-03-19 00:00:00', '2022-03-21 00:00:00', 'CK152136164657391');
INSERT INTO `tb_purchase_item` VALUES ('29', 'CGYY152161559277062', '7', '蛇胆川贝液', '1.00', '2.00', '10', '10.00', '2018-03-21 15:49:30', '2018-03-21 15:49:30', '待入库', '20181221', '2018-12-21 00:00:00', '2022-03-21 00:00:00', 'CK152136160201236');
INSERT INTO `tb_purchase_item` VALUES ('30', 'CGYY152161559277062', '10', '氯霉素片', '10.00', '12.00', '10', '100.00', '2018-03-21 17:14:34', '2018-03-21 17:14:34', '待入库', '20180327', '2018-03-27 00:00:00', '2022-04-08 00:00:00', 'CK152136160296498');
INSERT INTO `tb_purchase_item` VALUES ('31', 'CGYY152161559277062', '2', '去痛片', '1.00', '2.00', '2', '2.00', '2018-03-21 17:24:03', '2018-03-21 17:24:03', '待入库', '20180421', '2018-04-21 00:00:00', '2020-03-21 00:00:00', 'CK152136160122306');

-- ----------------------------
-- Table structure for `tb_sales`
-- ----------------------------
DROP TABLE IF EXISTS `tb_sales`;
CREATE TABLE `tb_sales` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sales_no` varchar(20) DEFAULT NULL COMMENT '销药单序列号',
  `patient_id` varchar(20) DEFAULT NULL COMMENT '客户',
  `total_quantity` int(11) DEFAULT NULL COMMENT '总数量',
  `total_price` decimal(20,2) DEFAULT NULL COMMENT '总价格',
  `operator` varchar(20) DEFAULT NULL COMMENT '操作员',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_sales
-- ----------------------------
INSERT INTO `tb_sales` VALUES ('8', 'XSYY152159260859817', 'P151668680822754', '3', '50.00', '小白', '2018-03-21 08:36:48', '2018-03-21 08:36:48');
INSERT INTO `tb_sales` VALUES ('9', 'XSYY152159265558704', 'P152147947944987', '15', '210.00', '小白', '2018-03-21 08:37:35', '2018-03-21 08:37:35');
INSERT INTO `tb_sales` VALUES ('10', 'XSYY152159283861553', 'P152147930666522', '5', '131.00', '小白', '2018-03-21 08:40:38', '2018-03-21 08:40:38');
INSERT INTO `tb_sales` VALUES ('11', 'XSYY152161117472448', 'P152147947944987', '5', '69.00', '小白', '2018-03-21 13:46:14', '2018-03-21 13:46:14');
INSERT INTO `tb_sales` VALUES ('12', 'XSYY152161126230208', 'P152147930666522', '15', '750.00', '小白', '2018-03-21 13:47:42', '2018-03-21 13:47:42');

-- ----------------------------
-- Table structure for `tb_sales_item`
-- ----------------------------
DROP TABLE IF EXISTS `tb_sales_item`;
CREATE TABLE `tb_sales_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sales_no` varchar(20) NOT NULL COMMENT '销药单序列号',
  `drug_id` int(11) DEFAULT NULL COMMENT '医药id',
  `drug_name` varchar(20) DEFAULT NULL COMMENT '医药名称',
  `sale_price` decimal(20,2) DEFAULT NULL COMMENT '销药单价',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `sale_total_price` decimal(20,2) DEFAULT NULL COMMENT '销售总价',
  `create_time` datetime DEFAULT NULL COMMENT '参加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `batch_no` varchar(10) DEFAULT NULL COMMENT '批次',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_sales_item
-- ----------------------------
INSERT INTO `tb_sales_item` VALUES ('14', 'XSYY152159260859817', '1', '阿司匹林肠溶片', '24.00', '1', '24.00', '2018-03-21 08:36:48', '2018-03-21 08:36:48', '20140303');
INSERT INTO `tb_sales_item` VALUES ('15', 'XSYY152159260859817', '2', '去痛片', '13.00', '2', '26.00', '2018-03-21 08:36:48', '2018-03-21 08:36:48', '20170119');
INSERT INTO `tb_sales_item` VALUES ('16', 'XSYY152159265558704', '6', '999感冒灵颗粒', '12.00', '5', '60.00', '2018-03-21 08:37:35', '2018-03-21 08:37:35', '20160215');
INSERT INTO `tb_sales_item` VALUES ('17', 'XSYY152159265558704', '7', '蛇胆川贝液', '15.00', '10', '150.00', '2018-03-21 08:37:35', '2018-03-21 08:37:35', '20160307');
INSERT INTO `tb_sales_item` VALUES ('18', 'XSYY152159283861553', '2', '去痛片', '13.00', '2', '26.00', '2018-03-21 08:40:38', '2018-03-21 08:40:38', '20170119');
INSERT INTO `tb_sales_item` VALUES ('19', 'XSYY152159283861553', '5', '氯酚黄敏', '35.00', '3', '105.00', '2018-03-21 08:40:38', '2018-03-21 08:40:38', '20150728');
INSERT INTO `tb_sales_item` VALUES ('20', 'XSYY152161117472448', '6', '999感冒灵颗粒', '12.00', '2', '24.00', '2018-03-21 13:46:14', '2018-03-21 13:46:14', '20160215');
INSERT INTO `tb_sales_item` VALUES ('21', 'XSYY152161117472448', '7', '蛇胆川贝液', '15.00', '3', '45.00', '2018-03-21 13:46:14', '2018-03-21 13:46:14', '20160307');
INSERT INTO `tb_sales_item` VALUES ('22', 'XSYY152161126230208', '13', '大黄碳酸氢钠片', '50.00', '15', '750.00', '2018-03-21 13:47:42', '2018-03-21 13:47:42', '20160103');

-- ----------------------------
-- Table structure for `tb_stock`
-- ----------------------------
DROP TABLE IF EXISTS `tb_stock`;
CREATE TABLE `tb_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `warehouse_no` varchar(20) DEFAULT NULL COMMENT '仓库',
  `batch_no` varchar(10) DEFAULT NULL,
  `drug_id` int(11) DEFAULT NULL COMMENT '医药id',
  `stock_quantity` int(11) DEFAULT NULL COMMENT '当前库存数量',
  `min_quantity` int(11) DEFAULT NULL COMMENT '最低库存数量',
  `max_quantity` int(11) DEFAULT NULL COMMENT '最高库存数量',
  `operator` varchar(20) DEFAULT NULL COMMENT '操作员',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '有效日期',
  `waring` varchar(50) DEFAULT NULL COMMENT '进库警告',
  `valid_waring` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_stock
-- ----------------------------
INSERT INTO `tb_stock` VALUES ('1', 'CK152136160122306', '20140303', '1', '49', '50', '500', '小白', '2018-03-19 17:18:44', '2018-03-21 08:36:48', null, '该药品已经过期18天，请进行退货或销毁处理！');
INSERT INTO `tb_stock` VALUES ('2', 'CK152136160122306', '20170119', '2', '136', '50', '500', '小白', '2018-03-19 17:18:50', '2018-03-21 08:40:38', null, null);
INSERT INTO `tb_stock` VALUES ('3', 'CK152136160122306', '20150315', '3', '150', '50', '1000', '小白', '2018-03-19 17:18:53', '2018-03-19 21:13:50', null, '该药品已经过期6天，请进行退货或销毁处理！');
INSERT INTO `tb_stock` VALUES ('4', 'CK152136160122306', '20160919', '4', '250', '50', '500', '小白', '2018-03-19 17:18:58', '2018-03-19 21:43:28', null, null);
INSERT INTO `tb_stock` VALUES ('5', 'CK152136160201236', '20150728', '5', '77', '200', '500', '小白', '2018-03-19 19:27:29', '2018-03-21 08:40:38', '当前库存量低于库存下限，请及时采药入库！', null);
INSERT INTO `tb_stock` VALUES ('6', 'CK152136160201236', '20160215', '6', '193', '100', '500', '小白', '2018-03-19 19:27:34', '2018-03-21 13:46:14', null, null);
INSERT INTO `tb_stock` VALUES ('7', 'CK152136160201236', '20160307', '7', '132', '100', '500', '小白', '2018-03-19 19:27:37', '2018-03-21 13:46:14', null, '该药品已经过期14天，请进行退货或销毁处理！');
INSERT INTO `tb_stock` VALUES ('8', 'CK152136160201236', '20170619', '8', '180', '300', '1000', '小白', '2018-03-19 19:27:45', '2018-03-19 21:20:52', '当前库存量低于库存下限，请及时采药入库！', null);
INSERT INTO `tb_stock` VALUES ('9', 'CK152136160296498', '20161020', '9', '475', '200', '1500', '小白', '2018-03-19 19:41:06', '2018-03-19 21:21:19', null, null);
INSERT INTO `tb_stock` VALUES ('10', 'CK152136160296498', '20160415', '10', '235', '200', '500', '小白', '2018-03-19 19:41:08', '2018-03-19 21:21:47', null, null);
INSERT INTO `tb_stock` VALUES ('11', 'CK152136160296498', '20170819', '11', '200', '300', '1000', '小白', '2018-03-19 19:41:11', '2018-03-19 19:41:11', '当前库存量低于库存下限，请及时采药入库！', null);
INSERT INTO `tb_stock` VALUES ('12', 'CK152136170963482', '20161212', '14', '250', '200', '500', '小白', '2018-03-19 19:59:45', '2018-03-19 19:59:45', null, null);
INSERT INTO `tb_stock` VALUES ('13', 'CK152136170963482', '20140919', '20', '400', '100', '500', '小白', '2018-03-19 20:07:13', '2018-03-19 20:07:13', null, '该药品已经过期2天，请进行退货或销毁处理！');
INSERT INTO `tb_stock` VALUES ('14', 'CK152136160296498', '20161016', '18', '200', '100', '500', '小白', '2018-03-19 20:07:21', '2018-03-19 20:07:21', null, null);
INSERT INTO `tb_stock` VALUES ('15', 'CK152136160201236', '20160201', '17', '100', '200', '600', '小白', '2018-03-19 20:07:26', '2018-03-19 20:07:26', '当前库存量低于库存下限，请及时采药入库！', null);
INSERT INTO `tb_stock` VALUES ('16', 'CK152136160122306', '20160815', '16', '388', '200', '500', '小白', '2018-03-19 20:09:06', '2018-03-19 21:42:51', null, null);
INSERT INTO `tb_stock` VALUES ('17', 'CK152136175056922', '20150331', '15', '140', '100', '500', '小白', '2018-03-19 20:09:12', '2018-03-19 21:42:11', null, '该药品还有11天将失效，请尽快销售！');
INSERT INTO `tb_stock` VALUES ('18', 'CK152136164657391', '20150215', '12', '500', '200', '1000', '小白', '2018-03-19 20:10:00', '2018-03-19 20:10:00', null, '该药品已经过期21天，请进行退货或销毁处理！');
INSERT INTO `tb_stock` VALUES ('19', 'CK152136164657391', '20160103', '13', '185', '100', '800', '小白', '2018-03-19 20:10:03', '2018-03-21 13:47:42', null, '该药品已经过期77天，请进行退货或销毁处理！');
INSERT INTO `tb_stock` VALUES ('20', 'CK152136170963482', '20160228', '19', '250', '300', '1000', '小白', '2018-03-19 20:14:23', '2018-03-19 20:14:23', '当前库存量低于库存下限，请及时采药入库！', '该药品还有7天将失效，请尽快销售！');
INSERT INTO `tb_stock` VALUES ('21', 'CK152136170963482', '20150501', '21', '600', '500', '1000', '小白', '2018-03-19 20:14:26', '2018-03-19 20:14:26', null, null);
INSERT INTO `tb_stock` VALUES ('22', 'CK152136160296498', '20171211', '1', '300', '50', '500', '小白', '2018-03-19 20:40:04', '2018-03-19 20:40:04', null, null);
INSERT INTO `tb_stock` VALUES ('23', 'CK152136160296498', '20160429', '1', '100', '50', '500', '小白', '2018-03-19 20:51:26', '2018-03-19 20:51:26', null, null);

-- ----------------------------
-- Table structure for `tb_warehouse`
-- ----------------------------
DROP TABLE IF EXISTS `tb_warehouse`;
CREATE TABLE `tb_warehouse` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `warehouse_no` varchar(20) DEFAULT NULL COMMENT '仓库编号',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location` varchar(255) DEFAULT NULL COMMENT '仓库位置',
  `manager` varchar(255) DEFAULT NULL COMMENT '仓库管理员',
  `phone` varchar(20) DEFAULT NULL COMMENT '操作员',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_warehouse
-- ----------------------------
INSERT INTO `tb_warehouse` VALUES ('1', 'CK152136160122306', '1号仓库', '1号大楼1楼', '小刘', '15623568795', '2018-03-21 00:25:23', '2018-03-20 09:51:24');
INSERT INTO `tb_warehouse` VALUES ('2', 'CK152136160201236', '2号仓库', '1号大楼2楼', '小陈', '15698635248', '2018-03-19 20:26:01', '2018-03-19 16:40:47');
INSERT INTO `tb_warehouse` VALUES ('3', 'CK152136160296498', '3号仓库', '1号大楼3楼', '黄小明', '16352987439', '2018-03-18 16:26:42', '2018-03-18 16:26:42');
INSERT INTO `tb_warehouse` VALUES ('4', 'CK152136164657391', '4号仓库', '2号大楼1楼', '陈明', '16598745639', '2018-03-18 16:27:26', '2018-03-18 16:27:26');
INSERT INTO `tb_warehouse` VALUES ('5', 'CK152136170963482', '5号仓库', '2号大楼2楼', '谢逊', '13698546856', '2018-03-18 16:28:29', '2018-03-18 16:28:29');
INSERT INTO `tb_warehouse` VALUES ('6', 'CK152136175056922', '6号仓库', '2号大楼3楼', '任得意', '15489632697', '2018-03-19 20:29:10', '2018-03-19 16:48:31');

-- ----------------------------
-- Table structure for `t_diagnosis`
-- ----------------------------
DROP TABLE IF EXISTS `t_diagnosis`;
CREATE TABLE `t_diagnosis` (
  `diagnosis_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '诊断书编号',
  `symptom` varchar(255) DEFAULT '' COMMENT '诊断症状',
  `doctor_id` varchar(20) DEFAULT '0' COMMENT '医生id',
  `patient_id` varchar(20) DEFAULT '0' COMMENT '病人id',
  `disease` varchar(255) DEFAULT NULL COMMENT '诊断疾病',
  `body_status` varchar(255) DEFAULT NULL COMMENT '身体情况',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`diagnosis_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_diagnosis
-- ----------------------------
INSERT INTO `t_diagnosis` VALUES ('1', '头晕，脑铉', '123456', 'P152147930666522', '低血糖', '虚弱', '2018-03-20 01:08:43', '2018-03-20 01:08:43');
INSERT INTO `t_diagnosis` VALUES ('2', '头晕，脑铉', '123456', 'P151668680822754', '低血糖', '虚弱', '2018-03-20 01:29:10', '2018-03-20 01:29:10');
INSERT INTO `t_diagnosis` VALUES ('3', '拉吐，呕吐，发热。', '123456', 'P151668680822754', '低血糖', '虚弱', '2018-03-20 11:11:40', '2018-03-20 11:11:40');
INSERT INTO `t_diagnosis` VALUES ('4', '咳嗽，流鼻涕。', '123456', 'P152147930666522', '感冒', '良好', '2018-03-20 14:27:11', '2018-03-20 14:27:11');
INSERT INTO `t_diagnosis` VALUES ('5', '咳嗽，流鼻涕。', '123456', 'P151668680822758', '感冒', '虚弱.', '2018-03-20 14:35:37', '2018-03-20 14:35:37');
INSERT INTO `t_diagnosis` VALUES ('6', '咳嗽，流鼻涕。', '123456', 'P151668680822758', '感冒', '虚弱.', '2018-03-20 14:39:37', '2018-03-20 14:39:37');
INSERT INTO `t_diagnosis` VALUES ('7', '咳嗽，流鼻涕。', '123456', 'P151625097777640', '感冒', '良好', '2018-03-20 14:53:20', '2018-03-20 14:53:20');
INSERT INTO `t_diagnosis` VALUES ('8', '咳嗽，流鼻涕。', '123456', 'P152147947944987', '感冒', '虚弱.', '2018-03-20 15:03:06', '2018-03-20 15:03:06');
INSERT INTO `t_diagnosis` VALUES ('9', '咳嗽，流鼻涕。', '123456', 'P152147947944987', '感冒', '虚弱.', '2018-03-20 15:03:21', '2018-03-20 15:03:21');
INSERT INTO `t_diagnosis` VALUES ('10', '咳嗽，流鼻涕。', '123456', 'P152147947944987', '感冒', '虚弱', '2018-03-20 15:04:28', '2018-03-20 15:04:28');
INSERT INTO `t_diagnosis` VALUES ('11', '咳嗽，流鼻涕。', '123456', 'P152147930666522', '感冒', '虚弱', '2018-03-20 15:25:10', '2018-03-20 15:25:10');
INSERT INTO `t_diagnosis` VALUES ('12', '头脑发热', '123456', 'P152147930666522', '发烧', '虚弱', '2018-03-20 18:38:06', '2018-03-20 18:38:06');

-- ----------------------------
-- Table structure for `t_doctor`
-- ----------------------------
DROP TABLE IF EXISTS `t_doctor`;
CREATE TABLE `t_doctor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标识',
  `doctor_id` varchar(20) DEFAULT NULL COMMENT '医生id',
  `name` varchar(255) DEFAULT '' COMMENT '姓名',
  `post_id` bigint(20) DEFAULT '0' COMMENT '职称id',
  `post` varchar(255) DEFAULT '' COMMENT '职称',
  `sex` varchar(2) DEFAULT '' COMMENT '性别',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期',
  `memo` varchar(2000) DEFAULT '' COMMENT '备注',
  `mc_id` bigint(20) DEFAULT '0' COMMENT '科别',
  `mc_name` varchar(255) DEFAULT '' COMMENT '科别名称',
  `login_name` varchar(255) DEFAULT '' COMMENT '登录名称',
  `login_password` varchar(255) DEFAULT '' COMMENT '登录密码',
  `is_active` int(11) DEFAULT '0' COMMENT '是否激活',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginName` (`login_name`) USING BTREE,
  UNIQUE KEY `doctor_id` (`doctor_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_doctor
-- ----------------------------
INSERT INTO `t_doctor` VALUES ('1', '123456', '陈翔', '100', '主治医师', '男', '1989-12-16 10:30:24', '主治手术', '200', '内科', 'dadmin', '123', '1', '2018-01-23 01:53:10', '2018-01-23 01:53:14');

-- ----------------------------
-- Table structure for `t_medicalcourses`
-- ----------------------------
DROP TABLE IF EXISTS `t_medicalcourses`;
CREATE TABLE `t_medicalcourses` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '科别编号',
  `name` varchar(255) DEFAULT '' COMMENT '科别名称',
  `type` int(11) DEFAULT '0' COMMENT '科别类型',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_medicalcourses
-- ----------------------------

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标识\r\n',
  `notice_id` bigint(20) DEFAULT NULL COMMENT '公告id',
  `title` varchar(255) DEFAULT NULL COMMENT '主题',
  `type` varchar(255) DEFAULT NULL COMMENT '类别',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `add_person` varchar(20) DEFAULT NULL COMMENT '添加人',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `notice_id` (`notice_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('1', '151945728915100', '789', '456', '123', '管理员', '2018-02-24 15:28:09', '2018-02-25 20:56:48');

-- ----------------------------
-- Table structure for `t_patient`
-- ----------------------------
DROP TABLE IF EXISTS `t_patient`;
CREATE TABLE `t_patient` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标识',
  `patient_id` varchar(20) DEFAULT NULL COMMENT '病人id',
  `name` varchar(255) DEFAULT '' COMMENT '姓名',
  `sex` varchar(2) DEFAULT '' COMMENT '性别',
  `address` varchar(255) DEFAULT '' COMMENT '地址',
  `age` int(11) DEFAULT '0' COMMENT '年龄',
  `is_finished` varchar(2) DEFAULT '0' COMMENT '是否处理',
  `mc_name` varchar(255) DEFAULT '' COMMENT '科别名称',
  `person_type` varchar(255) DEFAULT NULL COMMENT '人群类型(正常，残障人士，孤寡老人）',
  `phone` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `login_name` varchar(255) DEFAULT NULL COMMENT '登录账号',
  `login_password` varchar(255) DEFAULT NULL COMMENT '登录密码',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`) USING BTREE,
  UNIQUE KEY `patient_id` (`patient_id`) USING BTREE,
  UNIQUE KEY `login_name` (`login_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_patient
-- ----------------------------
INSERT INTO `t_patient` VALUES ('4', 'P151668680822754', '阿一', '男', '北京市海淀区上地十号七栋2单元110室', '26', '是', '大内科', '孤寡老人', '13204056704', 'pcurr8', 'pcurry', '2017-07-07 16:00:00', '2018-03-20 15:21:35');
INSERT INTO `t_patient` VALUES ('5', 'P151999824227608', '阿二', '男', '美国金州', '22', '否', '大内科', '正常', '15667778845', 'ehjw', '123', '2018-03-04 15:44:02', '2018-03-20 15:21:31');
INSERT INTO `t_patient` VALUES ('6', 'P152147930666522', '百度熊', '男', '东莞理工学院松山湖校区', '22', '是', '妇儿科室', '正常', '13204056777', 'p77777', '145', '2018-03-20 01:08:27', '2018-03-20 01:08:27');
INSERT INTO `t_patient` VALUES ('7', 'P152147947944987', '阿三', '男', '北京市海淀区上地十号七栋2单元110室', '26', '是', '妇儿科室', '残障人士', '13204056788', 'p12345', '123', '2018-03-20 01:11:19', '2018-03-20 01:11:19');
INSERT INTO `t_patient` VALUES ('8', 'P151625097777640', '阿三', '男', '北京市海淀区上地十号七栋2单元110室', '22', '否', '大外科', '正常', '13204056796', 'p333', 'p888', '2017-11-07 00:00:00', '2017-07-01 00:00:00');
INSERT INTO `t_patient` VALUES ('9', 'P151668680822758', '阿四', '男', '北京市海淀区上地十号七栋2单元110室', '26', '是', '大内科', '正常', '13204056781', 'p222', 'pcurry', '2017-07-03 00:00:00', '2017-07-03 00:00:00');

-- ----------------------------
-- Table structure for `t_prescription`
-- ----------------------------
DROP TABLE IF EXISTS `t_prescription`;
CREATE TABLE `t_prescription` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标识',
  `prescription_id` varchar(20) DEFAULT NULL COMMENT '处方id',
  `drug_data` varchar(255) DEFAULT NULL COMMENT '药品数据（json格式，含药品名称，药品数量，药品用量）',
  `patient_id` varchar(20) DEFAULT NULL COMMENT '病人id',
  `doctor_id` varchar(20) DEFAULT NULL COMMENT '医生id',
  `is_deal` varchar(20) DEFAULT NULL COMMENT '是否处理（默认未处理）',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `prescription_id` (`prescription_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_prescription
-- ----------------------------
INSERT INTO `t_prescription` VALUES ('3', 'Pr152151138072042', '[{\"drugName\":\"阿司匹林肠溶片\",\"drugNum\":\"1盒\",\"drugUsage\":\"1日2次，4-6小时1次。\"},{\"drugName\":\"去痛片\",\"drugNum\":\"2盒\",\"drugUsage\":\"需要时服用 一次1-2片，一日1-3次。\"}]', 'P151668680822754', '123456', '已处理', '2018-03-19 14:03:01', '2018-03-20 20:40:51');
INSERT INTO `t_prescription` VALUES ('4', 'Pr152152958472901', '[{\"drugName\":\"999感冒灵颗粒\",\"drugNum\":\"5袋\",\"drugUsage\":\"开水冲服，一次10克，一日3次。\"},{\"drugName\":\"蛇胆川贝液\",\"drugNum\":\"10支\",\"drugUsage\":\"本品口服。一次10 毫升，一日2次。\"}]', 'P152147947944987', '123456', '已处理', '2018-03-19 15:06:25', '2018-03-20 18:40:59');
INSERT INTO `t_prescription` VALUES ('5', 'Pr152154240149631', '[{\"drugName\":\"去痛片\",\"drugNum\":\"2盒\",\"drugUsage\":\"需要时服用 一次1-2片，一日1-3次。\"},{\"drugName\":\"氯酚黄敏\",\"drugNum\":\"3板\",\"drugUsage\":\"开水冲服，一次10克，一日3次。\"}]', 'P152147930666522', '123456', '已处理', '2018-03-20 18:40:01', '2018-03-20 19:40:59');
INSERT INTO `t_prescription` VALUES ('6', 'Pr152154616660998', '[{\"drugName\":\"999感冒灵颗粒\",\"drugNum\":\"2袋\",\"drugUsage\":\"开水冲服，一次10克，一日3次。\"},{\"drugName\":\"蛇胆川贝液\",\"drugNum\":\"3支\",\"drugUsage\":\"本品口服。一次10 毫升，一日2次。\"}]', 'P152147947944987', '123456', '已处理', '2018-03-20 19:42:47', '2018-03-20 19:50:23');
INSERT INTO `t_prescription` VALUES ('7', 'Pr152154660184696', '[{\"drugName\":\"大黄碳酸氢钠片\",\"drugNum\":\"15瓶\",\"drugUsage\":\"口服，一次1～3片，一日3次。\"}]', 'P152147930666522', '123456', '已处理', '2018-03-20 19:50:02', '2018-03-20 19:50:02');

-- ----------------------------
-- Table structure for `t_sickbed`
-- ----------------------------
DROP TABLE IF EXISTS `t_sickbed`;
CREATE TABLE `t_sickbed` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标识',
  `sickbed_id` varchar(20) DEFAULT NULL COMMENT '病床id',
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  `is_free` tinyint(2) DEFAULT NULL COMMENT '是否空闲',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sickbed_id` (`sickbed_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sickbed
-- ----------------------------

-- ----------------------------
-- Table structure for `t_stay_hospital`
-- ----------------------------
DROP TABLE IF EXISTS `t_stay_hospital`;
CREATE TABLE `t_stay_hospital` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标识',
  `patient_id` varchar(20) DEFAULT NULL COMMENT '病人id',
  `sickbed_id` varchar(20) DEFAULT NULL COMMENT '病床id',
  `time` date DEFAULT NULL COMMENT '入院时间',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_stay_hospital
-- ----------------------------