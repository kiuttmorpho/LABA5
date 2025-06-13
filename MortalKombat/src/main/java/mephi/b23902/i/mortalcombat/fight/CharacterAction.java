/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b23902.i.mortalcombat.fight;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import mephi.b23902.i.mortalcombat.enemy_fabrics.EnemyFabric;
import mephi.b23902.i.mortalcombat.enemys.Baraka;
import mephi.b23902.i.mortalcombat.enemys.LiuKang;
import mephi.b23902.i.mortalcombat.enemys.ShaoKahn;
import mephi.b23902.i.mortalcombat.enemys.SonyaBlade;
import mephi.b23902.i.mortalcombat.enemys.SubZero;
import mephi.b23902.i.mortalcombat.player.Human;
import mephi.b23902.i.mortalcombat.player.Items;
import mephi.b23902.i.mortalcombat.player.Player;

/**
 * Класс, управляющий основными игровыми механиками и поведением персонажей.
 * Отвечает за выбор противников, их поведение, систему уровней и использование предметов.
 */
public class CharacterAction {

    private int experience_for_next_level = 40;
    
    /** 
     * Матрица возможных типов атак противников.
     * Каждый подмассив представляет набор возможных атак для определенного типа поведения.
     */
    private final int kind_fight[][] = {{1, 0}, {1, 1, 0}, {0, 1, 0}, {1, 1, 1, 1}};

    private Player enemyes[] = new Player[6];
    private EnemyFabric fabric = new EnemyFabric();
    private Player enemyy = null;

    /**
     * Инициализирует массив противников с помощью фабрики.
     */
    public void setEnemyes() {
        enemyes[0] = fabric.create(0, 0);
        enemyes[1] = fabric.create(1, 0);
        enemyes[2] = fabric.create(2, 0);
        enemyes[3] = fabric.create(3, 0);
        enemyes[4] = fabric.create(4, 0);
        enemyes[5] = fabric.create(4, 0);
    }

    /**
     * Возвращает массив противников.
     * @return массив объектов Player, представляющих противников
     */
    public Player[] getEnemyes() {
        return this.enemyes;
    }

    /**
     * Выбирает случайного противника и обновляет GUI.
     * @param label метка для изображения противника
     * @param label2 метка для имени противника
     * @param text метка для урона противника
     * @param label3 метка для здоровья противника
     * @return выбранный противник
     */
    public Player ChooseEnemy(JLabel label, JLabel label2, JLabel text, JLabel label3) {
        int i = (int) (Math.random() * 4);
        switch (i) {
            case 0:
                enemyy = enemyes[0];
                break;
            case 1:
                enemyy = enemyes[1];
                break;
            case 2:
                enemyy = enemyes[2];
                break;
            case 3:
                enemyy = enemyes[3];
                break;
        }
        label.setIcon(enemyy.getPicture());
        label2.setText(enemyy.getName());
        text.setText(String.valueOf(enemyy.getDamage()));
        label3.setText(Integer.toString(enemyy.getHealth()) + "/" + Integer.toString(enemyy.getMaxHealth()));
        return enemyy;
    }

    /**
     * Выбирает босса (Shao Kahn) и настраивает его параметры в зависимости от уровня игрока.
     * @param label метка для изображения босса
     * @param label2 метка для имени босса
     * @param text метка для урона босса
     * @param label3 метка для здоровья босса
     * @param i параметр для настройки (не используется)
     * @param human объект игрока
     * @return выбранный босс
     */
    public Player ChooseBoss(JLabel label, JLabel label2, JLabel text, JLabel label3, int i, Player human) {
        label2.setText("Shao Kahn - БОСС");
        enemyy = enemyes[4];
        for(int j=0; j<human.getLevel() - enemyy.getLevel() + 2; i++) {
            enemyy.setLevel();
        }
        enemyy.setDamage(enemyy.getLevel());
        enemyy.setMaxHealth(5 * enemyy.getLevel());
        enemyy.setHealth(enemyy.getMaxHealth() - enemyy.getHealth());
        System.out.println("health: " + enemyy.getHealth());
        label.setIcon(enemyy.getPicture());
        text.setText(String.valueOf(enemyy.getDamage()));
        label3.setText(Integer.toString(enemyy.getHealth()) + "/" + Integer.toString(enemyy.getMaxHealth()));
        return enemyy;
    }

    /**
     * Определяет поведение противника на основе случайного числа и вероятностей.
     * @param k1 вероятность первого типа поведения
     * @param k2 вероятность второго типа поведения
     * @param k3 вероятность третьего типа поведения
     * @param k4 вероятность четвертого типа поведения
     * @param i случайное число (0-1)
     * @param isWizard является ли противник магом
     * @return массив, представляющий последовательность атак
     */
    public int[] EnemyBehavior(int k1, int k2, int k3, int k4, double i, Boolean isWizard) {
        int arr[] = null;
        if (i < k1 * 0.01) {
            arr = kind_fight[0];
        }
        if (i >= k1 * 0.01 & i < (k1 + k2) * 0.01) {
            arr = kind_fight[1];
        }
        if (i >= (k1 + k2) * 0.01 & i < (k1 + k2 + k3) * 0.01) {
            arr = kind_fight[2];
        }
        if (i >= (k1 + k2 + k3) * 0.01 & i < 1) {
            arr = kind_fight[3];
        }
        return arr;
    }

    /**
     * Выбирает поведение для конкретного типа противника.
     * @param enemy противник
     * @param action экземпляр CharacterAction
     * @return массив, представляющий последовательность атак
     */
    public int[] ChooseBehavior(Player enemy, CharacterAction action) {
        int arr[] = null;
        double i = Math.random();
        if (enemy instanceof Baraka) {
            arr = action.EnemyBehavior(15, 15, 60, 10, i, false);
        }
        if (enemy instanceof SubZero) {
            arr = action.EnemyBehavior(25, 25, 0, 50, i, true);
        }
        if (enemy instanceof LiuKang) {
            arr = action.EnemyBehavior(13, 13, 10, 64, i, false);
        }
        if (enemy instanceof SonyaBlade) {
            arr = action.EnemyBehavior(25, 25, 50, 0, i, false);
        }
        if (enemy instanceof ShaoKahn) {
            arr = action.EnemyBehavior(10, 45, 0, 45, i, false);
        }
        return arr;
    }

    /**
     * Обновляет прогресс-бар здоровья игрока или противника.
     * @param player игрок или противник
     * @param progress прогресс-бар для обновления
     */
    public void HP(Player player, JProgressBar progress) {
        if (player.getHealth() >= 0) {
            progress.setValue(player.getHealth());
        } else {
            progress.setValue(0);
        }
    }
    
    /**
     * Метод для использования слабости (заглушка).
     */
    public void useWeakness() {
        // Реализация будет добавлена позже
    }

    /**
     * Добавляет очки опыта и проверяет повышение уровня.
     * @param human игрок
     * @param enemyes массив противников
     * @return true, если уровень повышен, иначе false
     */
    public Boolean AddPoints(Human human, Player[] enemyes) {
        switch (human.getLevel()) {
            case 0:
                human.setExperience(20);
                human.setPoints(25 + human.getHealth() / 4);
                break;
            case 1:
                human.setExperience(25);
                human.setPoints(30 + human.getHealth() / 4);
                break;
            case 2:
                human.setExperience(30);
                human.setPoints(35 + human.getHealth() / 4);
                break;
            case 3:
                human.setExperience(40);
                human.setPoints(45 + human.getHealth() / 4);
                break;
            case 4:
                human.setExperience(50);
                human.setPoints(55 + human.getHealth() / 4);
                break;
        }
        Boolean isLevelUp = false;
        System.out.println(experience_for_next_level + " и " +  human.getExperience());
        
        if (experience_for_next_level <= human.getExperience()) {   
            human.setLevel();
            isLevelUp = true;
            experience_for_next_level += human.getExperience();
            human.setNextExperience(experience_for_next_level);
            NewHealthHuman(human);
            for (int j = 0; j < 4; j++) {
                NewHealthEnemy(enemyes[j], human);
            }
        }
        
        return isLevelUp;
    }

    /**
     * Добавляет очки после победы над боссом.
     * @param human игрок
     * @param enemyes массив противников
     */
    public void AddPointsBoss(Human human, Player[] enemyes) {
        switch (human.getLevel()) {
            case 2:
                human.setExperience(30);
                human.setPoints(45 + human.getHealth() / 2);
                break;
            case 4:
                human.setExperience(50);
                human.setPoints(65 + human.getHealth() / 2);
                break;
        }
        if (experience_for_next_level <= human.getExperience()) {
            human.setLevel();
            experience_for_next_level += human.getExperience();
            human.setNextExperience(experience_for_next_level);
            NewHealthHuman(human);
            for (int j = 0; j < 4; j++) {
                NewHealthEnemy(enemyes[j], human);
            }
        }
    }

    /**
     * Добавляет предметы в инвентарь на основе вероятностей.
     * @param k1 вероятность получения первого предмета
     * @param k2 вероятность получения второго предмета
     * @param k3 вероятность получения третьего предмета
     * @param items массив предметов
     */
    public void AddItems(int k1, int k2, int k3, Items[] items) {
        double i = Math.random();
        if (i < k1 * 0.01) {
            items[0].setCount(1);
        }
        if (i >= k1 * 0.01 & i < (k1 + k2) * 0.01) {
            items[1].setCount(1);
        }
        if (i >= (k1 + k2) * 0.01 & i < (k1 + k2 + k3) * 0.01) {
            items[2].setCount(1);
        }
    }

    /**
     * Обновляет параметры здоровья и урона игрока при повышении уровня.
     * @param human игрок
     */
    public void NewHealthHuman(Human human) {
        int hp = 0;
        int damage = 0;
        switch (human.getLevel()) {
            case 1:
                hp = 25;
                damage = 3;
                break;
            case 2:
                hp = 30;
                damage = 3;
                break;
            case 3:
                hp = 30;
                damage = 4;
                break;
            case 4:
                hp = 40;
                damage = 6;
                break;
        }
        human.setMaxHealth(hp);
        human.setDamage(damage);
    }

    /**
     * Обновляет параметры противников при повышении уровня игрока.
     * @param enemy противник
     * @param human игрок
     */
    public void NewHealthEnemy(Player enemy, Human human) {
        int hp = 0;
        int damage = 0;
        switch (human.getLevel()) {
            case 1:
                hp = 32;
                damage = 25;
                break;
            case 2:
                hp = 30;
                damage = 20;
                break;
            case 3:
                hp = 23;
                damage = 24;
                break;
            case 4:
                hp = 25;
                damage = 26;
                break;
        }
        enemy.setMaxHealth((int) enemy.getMaxHealth() * hp / 100);
        enemy.setDamage((int) enemy.getDamage() * damage / 100);
        enemy.setLevel();
    }

    /**
     * Использует предмет из инвентаря.
     * @param human игрок
     * @param items массив предметов
     * @param name имя предмета
     * @param dialog диалоговое окно для сообщений
     * @param dialog1 дополнительное диалоговое окно
     */
    public void UseItem(Player human, Items[] items, String name, JDialog dialog, JDialog dialog1) {
        switch (name) {
            case "jRadioButton1":
                if (items[0].getCount() > 0) {
                    human.setHealth((int) (human.getMaxHealth() * 0.25));
                    items[0].setCount(-1);
                } else {
                    dialog.setVisible(true);
                    dialog.setBounds(300, 200, 400, 300);
                }
                break;
            case "jRadioButton2":
                if (items[1].getCount() > 0) {
                    human.setHealth((int) (human.getMaxHealth() * 0.5));
                    items[1].setCount(-1);
                } else {
                    dialog.setVisible(true);
                    dialog.setBounds(300, 200, 400, 300);
                }
                break;
            case "jRadioButton3":
                dialog.setVisible(true);
                dialog.setBounds(300, 200, 400, 300);
                break;
        }
        
        if(dialog.isVisible()==false){
            dialog1.dispose();
        }
    }
}