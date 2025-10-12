# 🎮 Mario: Whac-A-Mole Game

A fun desktop Whac-A-Mole style game built in *Java Swing, featuring **Mario’s Monty Mole* and *Piranha Plants*!  
Smash the moles to earn points — but be careful not to click a Piranha Plant, or it’s *Game Over*! 🌱💥  

---

## 🧩 Features

- 🕹 Interactive 3x3 game board  
- 🐹 Moles appear randomly — click them to score points  
- 🌿 Piranha Plants appear as traps — clicking them ends the game  
- 🔁 *Restart button* to replay instantly  
- 🎨 Smooth image rendering with scaled icons  
- ⏱ Timed mole & plant spawns using Swing Timer

---

## 🖥 Gameplay

| Action | Effect |
|--------|---------|
| Click on a Mole 🐹 | +10 points |
| Click on a Piranha Plant 🌿 | Game Over ❌ |
| Press *Restart Game* | Resets score & starts a new round |

---

## 🧠 How It Works

- Two timers control gameplay:
  - *Mole Timer (1 second):* Randomly spawns moles on tiles.
  - *Plant Timer (1.5 seconds):* Randomly spawns/removes Piranha Plants.
- If a plant overlaps a mole, it skips that tile.
- Clicking logic:
  - If you click a *mole*: gain +10 points.
  - If you click a *plant*: game stops and all tiles are disabled.
 
## 📸 Game Output

<img width="1919" height="1003" alt="Screenshot 2025-10-12 174546" src="https://github.com/user-attachments/assets/f69c4bb0-a959-4e9e-920b-1d8ad8647ef1" />
