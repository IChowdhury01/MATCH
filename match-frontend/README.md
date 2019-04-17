
@All resources added as a comment within the code@

<Setup>
  
1. create-react-app Match

2. yarn add react-router-dom react-redux redux redux-actions immutable styled-components open-color

3. yarn start

<Frontend/Backend running process>

-Frontend

yarn start (react,node,yarn being installed)

-Backend

'mvn pakage' at match-services

'java -jar MATCH-1.0-SNAPSHOT.jar' at target

!adjust mysql usr,pwd!

<Front/Backend connection structure>

redux->container->component->container->backend API->redux->container

<File Directory for Frontend>
  
-used absolute path ./src => added NODE PATH in the package.json script

-components: presentational components

-containers: container components

-lib: function => api, some stlye handling func maybe added later for design issue (ex: shadow control)

-redux: redux code made in modules, which contain Action TYPE, Function and the Reducer

<Testing the Frontend state features>
  
https://chrome.google.com/webstore/detail/redux-devtools/lmhkpmbekcpmknklioeibfkpmmfibljd/related

used this chrome program to evaluate redux

<Redux & State change issue>

module -> action type, func, reducer combined

use setin

<Axios>
  
not working, deleted the code (one sample function made as a comment in Login.js)

<sample fetch code>
https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.<br>
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.<br>
You will also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.<br>
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.<br>
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br>
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can’t go back!**

If you aren’t satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (Webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you’re on your own.

You don’t have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn’t feel obligated to use this feature. However we understand that this tool wouldn’t be useful if you couldn’t customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

### Code Splitting

This section has moved here: https://facebook.github.io/create-react-app/docs/code-splitting

### Analyzing the Bundle Size

This section has moved here: https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size

### Making a Progressive Web App

This section has moved here: https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app

### Advanced Configuration

This section has moved here: https://facebook.github.io/create-react-app/docs/advanced-configuration

### Deployment

This section has moved here: https://facebook.github.io/create-react-app/docs/deployment

### `npm run build` fails to minify

This section has moved here: https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify
