import { defineConfig, loadEnv } from 'vite';
import { resolve } from 'path';

export default defineConfig(({ mode }) => {
    const env = loadEnv(mode, process.cwd(), '');
    const apiTarget = env.VITE_API_PROXY_TARGET || 'http://localhost:8081';

    return {
        root: '.',
        publicDir: 'public',
        build: {
            outDir: resolve(__dirname, '../memora-backend/src/main/resources/static'),
            emptyOutDir: true
        },
        server: {
            port: Number(env.VITE_DEV_PORT) || 5173,
            proxy: {
                '/usuarios': { target: apiTarget, changeOrigin: true },
                '/fontes-dados': { target: apiTarget, changeOrigin: true },
                '/solicitacoes': { target: apiTarget, changeOrigin: true },
                '/avaliacoes': { target: apiTarget, changeOrigin: true },
                '/estatisticas': { target: apiTarget, changeOrigin: true }
            }
        }
    };
});
