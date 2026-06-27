# BetterTTV Twitch Morphe Patches 🧩

A custom Morphe patch module to integrate BetterTTV (BTTV), FrankerFaceZ (FFZ), and 7TV emotes into the official Twitch Android app.

---

## 🩹 Patches List

<!-- PATCHES_START EXPANDED -->
#### A list of your patches will be automatically shown here after your first patches release is created.
<!-- PATCHES_END -->

---

## 🚀 How to Enable (Without Local Installation)

Since this repository is fully integrated with GitHub Actions, you do not need to install Java, Android SDK, or run any build commands locally.

### Step 1: Push to GitHub
1. Create a new repository on GitHub (e.g., `bettertwitchtv-morphe`).
2. Push this project to your new repository.

### Step 2: Trigger Release Build
1. Pushing to the `main` or `dev` branch automatically triggers the `.github/workflows/release.yml` workflow.
2. The workflow compiles the `bettertwitchtv` extension and `patches` module, packages them as a `.mpp` release file, and publishes it under GitHub Releases.

### Step 3: Add to Morphe Manager
1. Open the **Morphe Manager** app on your Android device.
2. Navigate to **Settings** -> **Repositories** (or Sources).
3. Add your GitHub repository URL:
   ```
   https://github.com/axel/bettertwitchtv-morphe
   ```
   *(Alternatively, click [here](https://morphe.software/add-source?github=axel/bettertwitchtv-morphe) to add it automatically).*
4. Under the **Patcher** tab, select the official Twitch APK, enable the **BetterTTV Integration** patch, and click **Patch**.

---

## 🧑‍💻 How it Works (Under the Hood)

- **`extensions/bettertwitchtv`**: Android library compiled to a `.dex` file (contained in `.mpe`). It holds the Java classes for fetching emotes from BTTV/FFZ/7TV APIs, parsing message tokens, and returning custom URLs.
- **`patches`**: Kotlin bytecode patch scripts that hook into `TwitchApplication.onCreate`, track broadcaster IDs, intercept incoming chat messages, and override emote URLs with custom CDN references.

---

## 📜 License

This project is licensed under the [GNU General Public License v3.0](LICENSE).
