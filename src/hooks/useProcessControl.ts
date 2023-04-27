import Process from "@/modules/process";
import { CardDirection, CardType, Cards, CardList } from "@/constant";
import { RollType } from "@/hooks/useGoBoth";

interface sideCardsInterface {
    direction: CardDirection;
    cardType?: CardType;
    cards?: Array<Cards>;
    cardList?: CardList;
    follow?: boolean;
}

interface ToolsInterface {
    rollType?: RollType,
    rollTime?: number,
}

type useProcessControlType = (
    header?: boolean,
    sideCard?: false | sideCardsInterface,
    footer?: boolean,
    tools?: ToolsInterface
) => void;

const useProcessControl: useProcessControlType = (
    header = true,
    sideCard = false,
    footer = true,
    tools = {
        rollType: RollType.time,
        rollTime: 1
    }
) => {
    Process.headerStatus.value = header;

    if(typeof sideCard === "boolean" && sideCard === false ) {
        Process.sideCardStatus.value = false;
    } else if(typeof sideCard === "object") {
        Process.sideCardStatus.value = true;
        Process.sideCardPosition.value = sideCard.direction;
        Process.sideCardType.value = sideCard.cardType == null ? CardType.Cards : sideCard.cardType;
        Process.sideCardChoice.value = sideCard.cards || [];
        if(sideCard.cardList != null) {
            Process.sideCardList.value = sideCard.cardList;
        }
        Process.sideCardFollow.value = sideCard.follow == null ? false : sideCard.follow;
    }
    
    Process.footerStatus.value = footer;

    // Process
    Process.rollType.value = tools.rollType!;
    Process.rollTime.value = tools.rollTime!;
}

export default useProcessControl;