import { registerPlugin } from '@capacitor/core';

import type { CapTorchPlugin } from './definitions';

const CapTorch = registerPlugin<CapTorchPlugin>('CapTorch', {
  web: () => import('./web').then(m => new m.CapTorchWeb()),
});

export type {CapTorchPlugin} from './definitions';
export { CapTorch };
