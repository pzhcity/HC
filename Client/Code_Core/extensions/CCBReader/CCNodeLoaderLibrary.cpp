#include "CCNodeLoaderLibrary.h"

#include "CCLayerLoader.h"
#include "CCLayerColorLoader.h"
#include "CCLayerGradientLoader.h"
#include "CCLabelBMFontLoader.h"
#include "CCLabelTTFLoader.h"
#include "CCSpriteLoader.h"
#include "CCScale9SpriteLoader.h"
#include "CCBFileLoader.h"
#include "CCBFileNewLoader.h"
#include "CCMenuLoader.h"
#include "CCMenuItemLoader.h"
#include "CCMenuItemImageLoader.h"
#include "CCControlButtonLoader.h"
#include "CCParticleSystemQuadLoader.h"
#include "CCScrollViewLoader.h"
#include "CCMenuItemCCBFileLoader.h"
#include "CCBClippingNodeLoader.h"


NS_CC_EXT_BEGIN

CCNodeLoaderLibrary::CCNodeLoaderLibrary() {

}

CCNodeLoaderLibrary::~CCNodeLoaderLibrary() {
    this->purge(true);
}

void CCNodeLoaderLibrary::registerDefaultCCNodeLoaders() {
    this->registerCCNodeLoader("CCNode", CCNodeLoader::loader());
    this->registerCCNodeLoader("CCLayer", CCLayerLoader::loader());
    this->registerCCNodeLoader("CCLayerColor", CCLayerColorLoader::loader());
    this->registerCCNodeLoader("CCLayerGradient", CCLayerGradientLoader::loader());
    this->registerCCNodeLoader("CCSprite", CCSpriteLoader::loader());
    this->registerCCNodeLoader("CCLabelBMFont", CCLabelBMFontLoader::loader());
    this->registerCCNodeLoader("CCLabelTTF", CCLabelTTFLoader::loader());
    this->registerCCNodeLoader("CCScale9Sprite", CCScale9SpriteLoader::loader());
    this->registerCCNodeLoader("CCScrollView", CCScrollViewLoader::loader());
    this->registerCCNodeLoader("CCBFile", CCBFileLoader::loader());
    this->registerCCNodeLoader("CCBFileNew", CCBFileNewLoader::loader());
    this->registerCCNodeLoader("CCMenu", CCMenuLoader::loader());
    this->registerCCNodeLoader("CCMenuItemImage", CCMenuItemImageLoader::loader());
	this->registerCCNodeLoader("CCMenuCCBFile", CCMenuItemCCBFileLoader::loader());
    this->registerCCNodeLoader("CCControlButton", CCControlButtonLoader::loader());
    this->registerCCNodeLoader("CCParticleSystemQuad", CCParticleSystemQuadLoader::loader());
	this->registerCCNodeLoader("CCBClippingNode", CCBClippingNodeLoader::loader());
}

void CCNodeLoaderLibrary::registerCCNodeLoader(const char * pClassName, CCNodeLoader * pCCNodeLoader) {
    pCCNodeLoader->retain();
    this->mCCNodeLoaders.insert(CCNodeLoaderMapEntry(pClassName, pCCNodeLoader));
}

void CCNodeLoaderLibrary::unregisterCCNodeLoader(const char * pClassName) {
    CCNodeLoaderMap::iterator ccNodeLoadersIterator = this->mCCNodeLoaders.find(pClassName);
    if (ccNodeLoadersIterator != this->mCCNodeLoaders.end())
    {
        ccNodeLoadersIterator->second->release();
        mCCNodeLoaders.erase(ccNodeLoadersIterator);
    }
    else
    {
        CCLOG("The loader (%s) doesn't exist", pClassName);
    }
}

CCNodeLoader * CCNodeLoaderLibrary::getCCNodeLoader(const char* pClassName) {
    CCNodeLoaderMap::iterator ccNodeLoadersIterator = this->mCCNodeLoaders.find(pClassName);
    assert(ccNodeLoadersIterator != this->mCCNodeLoaders.end());
    return ccNodeLoadersIterator->second;
}

void CCNodeLoaderLibrary::purge(bool pReleaseCCNodeLoaders) {
    if(pReleaseCCNodeLoaders) {
        for(CCNodeLoaderMap::iterator it = this->mCCNodeLoaders.begin(); it != this->mCCNodeLoaders.end(); it++) {
            it->second->release();
        }
    }
    this->mCCNodeLoaders.clear();
}



static CCNodeLoaderLibrary * sSharedCCNodeLoaderLibrary = NULL;

CCNodeLoaderLibrary * CCNodeLoaderLibrary::sharedCCNodeLoaderLibrary() {
    if(sSharedCCNodeLoaderLibrary == NULL) {
        sSharedCCNodeLoaderLibrary = new CCNodeLoaderLibrary();

        sSharedCCNodeLoaderLibrary->registerDefaultCCNodeLoaders();
    }
    return sSharedCCNodeLoaderLibrary;
}

void CCNodeLoaderLibrary::purgeSharedCCNodeLoaderLibrary() {
    CC_SAFE_DELETE(sSharedCCNodeLoaderLibrary);
}

CCNodeLoaderLibrary * CCNodeLoaderLibrary::newDefaultCCNodeLoaderLibrary() {
    CCNodeLoaderLibrary * ccNodeLoaderLibrary = CCNodeLoaderLibrary::library();
    
    ccNodeLoaderLibrary->registerDefaultCCNodeLoaders();

    return ccNodeLoaderLibrary;
}

NS_CC_EXT_END