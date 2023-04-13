import {createTheme} from '@mui/material/styles';

const MyTheme = createTheme({
  palette: {
    type: 'light',
    primary: {
      main: '#006464',
    },
    secondary: {
      main: '#00c5b7',
    },
    icon: {
      main: '#ffffff'
    },
    action: {
      disabled: '#ffffff'
    }
  },
});
export default MyTheme;